package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.base.BaseService;
import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.dao.IRedisDao;
import com.wy.weixin.model.User;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.HttpUtils;
import com.wy.weixin.util.Utils;
import com.wy.weixin.util.WeixinUtils;
import com.wy.weixin.util.XMLUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

@Service
public class WeixinService extends BaseService implements IWeixinService {

    private static final DecimalFormat DF = new DecimalFormat("#");

    @Autowired
    private IRedisDao redisDao;

    @Override
    public String getToken() {
        // 从redis缓存中取出token
        String token = redisDao.get("token");
        if (token == null || token.equals("")) {
            // 如果redis取值失败，则请求微信接口重新获取
            String url = WeixinUrlConstants.GET_TOKEN_URL
                    .replace("APPID", WeixinConfigConstant.APPID)
                    .replace("APPSECRET", WeixinConfigConstant.APPSECRET);
            try {
                JSONObject resultJson = HttpUtils.getJson(url);
                // 取出access_token
                String accessToken = resultJson.getString("access_token");
                // 过期时间
                long expires_in = resultJson.getLongValue("expires_in");
                // 将token存入redis中，并设置过期时间提前5秒钟，防止token过期，单位为秒
                redisDao.set("token", accessToken, expires_in - 5);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("获取Token出错！！" + e.getMessage());
                return "";
            }
        }
        return token;
    }

    @Override
    public String getJsApiTicket() {
        // 从redis缓存中取出jsapi_ticket
        String jsapi_ticket = redisDao.get("jsapi_ticket");
        if (jsapi_ticket == null || jsapi_ticket.equals("")) {
            // 如果redis取值失败，则请求微信接口重新获取
            String access_token = getToken();
            String url = WeixinUrlConstants.GET_JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
            try {
                JSONObject resultJson = HttpUtils.getJson(url);
                String errcode = resultJson.getString("errcode");
                String errmsg = resultJson.getString("errmsg");
                // 取出jsapi_ticket
                jsapi_ticket = resultJson.getString("ticket");
                // 过期时间
                long expires_in = resultJson.getLongValue("expires_in");
                // 讲jsapi_token存入redis中，并设置过期时间提前5秒钟，防止本地jsapi_ticket提前过期
                redisDao.set("jsapi_ticket", jsapi_ticket, expires_in - 5);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("获取jsapi_ticket出错！！" + e.getMessage());
                return "";
            }
        }
        return jsapi_ticket;
    }

    @Override
    public Map<String, String> jsApiTicketSign(String url) {
        // jsapi_ticket
        String jsApiTicket = getJsApiTicket();
        // 随机字符串
        String noncestr = Utils.nonceStr();
        // 时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 按照字段名称进行字典排序，后采用URL键值对的形式拼接字符串
        StringBuffer sb = new StringBuffer();
        sb.append("jsapi_ticket=").append(jsApiTicket)
                .append("&noncestr=").append(noncestr)
                .append("&timestamp=").append(timestamp)
                .append("&url=").append(url);
        // SHA1签名
        String signature = Utils.SHA1(sb.toString());

        Map<String, String> map = new HashMap<>();
        map.put("noncestr", noncestr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);

        return map;
    }

    @Override
    public Map<String, String> paySign(Map<String, String> paramMap) {
        // 构建微信支付统一下单接口参数
        SortedMap<String, String> map = new TreeMap<>();
        // 公众号ID
        map.put("appid", WeixinConfigConstant.APPID);
        // 商户号ID
        map.put("mch_id", WeixinConfigConstant.MCH_ID);
        // 设备号
        map.put("device_info", "WEB");
        // 随机字符串
        map.put("nonce_str", Utils.nonceStr());
        // 签名类型
        map.put("sign_type", "MD5");
        // 商品描述
        map.put("body", paramMap.get("body"));
        // 附加信息
        map.put("attach", paramMap.get("attach"));
        // 订单号
        map.put("out_trade_no", Utils.randomUUID());
        // 总金额
        map.put("total_fee", paramMap.get("totalFee"));
        // ip地址
        map.put("spbill_create_ip", paramMap.get("ip"));
        // 交易通知回调地址
        map.put("notify_url", paramMap.get("notifyUrl"));
        // 交易类型
        map.put("trade_type", "JSAPI");
        // 商品id
        map.put("product_id", paramMap.get("productId"));
        // 用户id
        map.put("openid", paramMap.get("openId"));
        // 参数签名
        String sign = WeixinUtils.sign(map);
        map.put("sign", sign);

        // map转xml格式
        String xmlParam = XMLUtil.mapToXml(map);
        try {
            // 请求微信统一支付接口
            String result = HttpUtils.post(WeixinUrlConstants.POST_PAY_UNIFIED_ORDER_URL, xmlParam);
            InputStream is = new ByteArrayInputStream(result.getBytes("utf-8"));
            // 解析xml格式为map
            Map<String, String> resultMap = XMLUtil.xmlISToMap(is);
            String returnCode = resultMap.get("return_code");   //SUCCESS
            String resultCode = resultMap.get("result_code");     //OK
            if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
                SortedMap<String, String> paySignMap = new TreeMap<>();
                paySignMap.put("appId", WeixinConfigConstant.APPID);
                paySignMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                paySignMap.put("nonceStr", Utils.nonceStr());
                paySignMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
                paySignMap.put("signType", "MD5");
                // 签名
                paySignMap.put("paySign", WeixinUtils.sign(paySignMap));
                // 解决java关键字package的问题
                paySignMap.put("packagee", "prepay_id=" + resultMap.get("prepay_id"));
                // 返回微信支付签名
                return paySignMap;
            } else {
                logger.error("获取微信支付签名失败！");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取微信支付签名失败！");
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error("获取微信支付签名失败！");
        }
        return null;
    }

    @Override
    public Map<String, String> getWebAuthToken(String code) {
        String url = WeixinUrlConstants.GET_WEBAUTH_TOKEN_URL
                .replace("APPID", WeixinConfigConstant.APPID)
                .replace("APPSECRET", WeixinConfigConstant.APPSECRET)
                .replace("CODE", code);
        try {
            JSONObject resultJson = HttpUtils.getJson(url);

            String access_token = resultJson.getString("access_token");
            String openid = resultJson.getString("openid");
            Map<String, String> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("openid", openid);

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取网页授权Token失败！！" + e.getMessage());
            return null;
        }
    }

    @Override
    public User getWebAuthUserInfo(String access_token, String openid) {
        String url = WeixinUrlConstants.GET_WEBAUTH_USERINFO_URL
                .replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
        try {
            JSONObject resultJson = HttpUtils.getJson(url);

            String nickname = resultJson.getString("nickname");
            int sex = resultJson.getIntValue("sex");
            String country = resultJson.getString("country");
            String province = resultJson.getString("province");
            String city = resultJson.getString("city");
            String headimgurl = resultJson.getString("headimgurl");
            User user = new User();
            user.setOpenid(openid);
            user.setNickname(nickname);
            user.setHeadImgUrl(headimgurl);
            user.setSex(sex);
            user.setCountry(country);
            user.setProvince(province);
            user.setCity(city);

            return user;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取用户信息出错！！" + e.getMessage());
            return null;
        }
    }

    @Override
    public User getWebAuthUserInfo(Map<String, String> map) {
        String access_token = map.get("access_token");
        String openid = map.get("openid");
        return getWebAuthUserInfo(access_token, openid);
    }

}

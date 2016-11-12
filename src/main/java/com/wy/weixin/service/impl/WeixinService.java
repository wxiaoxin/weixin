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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

@Service
public class WeixinService extends BaseService implements IWeixinService {

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
                    .replace("APPSECRET", WeixinConfigConstant.AppSECRET);
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
        if(jsapi_ticket == null || jsapi_ticket.equals("")) {
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
        String noncestr = "wy";
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
    public Map<String, String> getWebAuthToken(String code) {
        String url = WeixinUrlConstants.GET_WEBAUTH_TOKEN_URL
                        .replace("APPID", WeixinConfigConstant.APPID)
                        .replace("APPSECRET", WeixinConfigConstant.AppSECRET)
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

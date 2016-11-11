package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.base.BaseService;
import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.dao.IRedisDao;
import com.wy.weixin.model.User;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.HttpUtils;
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
        String token = redisDao.get("token");
        if (token != null && !token.equals("")) {
            logger.debug("Redis - " + token);
            return token;
        } else {
            String url = WeixinUrlConstants.GET_TOKEN_URL
                            .replace("APPID", WeixinConfigConstant.APPID)
                            .replace("APPSECRET", WeixinConfigConstant.AppSECRET);
            try {
                String result = HttpUtils.get(url);
                JSONObject json = JSON.parseObject(result);
                String accessToken = json.getString("access_token");
                // 将token存入redis中，并设置过期时间为15分钟
                redisDao.set("token", accessToken, 15);
                logger.debug("请求 - " + accessToken);

                return accessToken;
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("用户Token出错！！" + e.getMessage());
                return "";
            }
        }
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

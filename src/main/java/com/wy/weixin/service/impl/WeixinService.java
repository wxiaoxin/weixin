package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.dao.IRedisDao;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.HttpUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

@Service
public class WeixinService implements IWeixinService {

    protected Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    private IRedisDao redisDao;

    @Override
    public String getToken() {
        String token = redisDao.get("token");
        if (token != null && !token.equals("")) {
            LOGGER.debug("从Redis中取出token " + token);
            return token;
        } else {
            String url = WeixinUrlConstants.GET_TOKEN_URL.replace("APPID", WeixinConfigConstant.APPID).replace("APPSECRET", WeixinConfigConstant.AppSECRET);
            try {
                String result = HttpUtils.get(url);
                JSONObject json = JSON.parseObject(result);
                String accessToken = json.getString("access_token");
                // 将token存入redis中，并设置过期时间为15分钟
                redisDao.set("token", accessToken, 15);
                LOGGER.debug("请求获取token " + accessToken);
                return accessToken;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

}

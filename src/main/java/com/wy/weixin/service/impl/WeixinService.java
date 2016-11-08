package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

@Service
public class WeixinService implements IWeixinService {


    @Override
    public String getToken() {
        String url = WeixinUrlConstants.GET_TOKEN_URL.replace("APPID", WeixinConfigConstant.APPID).replace("APPSECRET", WeixinConfigConstant.AppSECRET);
        try {
            String result = HttpUtils.get(url);

            System.err.println(result);

            JSONObject json = JSON.parseObject(result);
            String accessToken = json.getString("access_token");
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {

        WeixinService weixinService = new WeixinService();
        String token = weixinService.getToken();
        System.out.println(token);

    }


}

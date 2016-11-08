package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.model.BuyTemplateMessage;
import com.wy.weixin.service.ITemplateMsgService;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 模板消息服务
 */

@Service
public class TemplateMsgService implements ITemplateMsgService {

    @Autowired
    private IWeixinService weixinService;

    @Override
    public void sendMsg(String touser, Map<String, String> data) {
        BuyTemplateMessage message = new BuyTemplateMessage();
        message.setTouser(touser);
        message.setTopcolor("#173177");
        message.parseData(data);

        String param = JSON.toJSONStringWithDateFormat(message, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
        try {
            String token = weixinService.getToken();
            String url = WeixinUrlConstants.POST_SEND_TM_URL.replace("ACCESS_TOKEN", token);
            String result = HttpUtils.post(url, param);
            System.err.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

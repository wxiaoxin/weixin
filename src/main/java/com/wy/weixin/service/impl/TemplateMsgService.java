package com.wy.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.constants.TemplateMsgConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.model.BuyTemplateMessage;
import com.wy.weixin.model.PhoneChargeTemplateMsg;
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
    public void sendBuyTMsg(String touser, Map<String, String> data) {
        BuyTemplateMessage message = new BuyTemplateMessage();
        message.setTouser(touser);
        message.setTopcolor("#173177");
        message.parseData(data);
        message.setUrl("http://www.baidu.com");
        String param = JSONObject.toJSONString(message);
        try {
            String token = weixinService.getToken();
            String url = WeixinUrlConstants.POST_SEND_TM_URL.replace("ACCESS_TOKEN", token);
            HttpUtils.post(url, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendPhoneChargeTMsg(String touser, String url, Map<String, String> data) {
        PhoneChargeTemplateMsg message = new PhoneChargeTemplateMsg();
        message.setTouser(touser);
        message.setUrl(url);
        message.setTopcolor(TemplateMsgConstant.PHONE_CHARGE_TM_COLOR);
        message.setTemplate_id(TemplateMsgConstant.PHONE_CHARGE_TM_ID);
        message.parseData(data);
        String param = JSONObject.toJSONString(message);
        try {
            String token = weixinService.getToken();
            String url2 = WeixinUrlConstants.POST_SEND_TM_URL.replace("ACCESS_TOKEN", token);
            HttpUtils.post(url2, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

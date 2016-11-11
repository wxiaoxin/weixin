package com.wy.weixin.model;

import com.wy.weixin.constants.TemplateMsgConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.9.
 *
 * 话费充值通知模板消息
 *
 * {{first.DATA}}
 * 手机号：{{keyword1.DATA}}
 * 充值金额：{{keyword2.DATA}}
 * {{remark.DATA}}
 */

public class PhoneChargeTemplateMsg {

    private String touser;

    private String template_id = TemplateMsgConstant.BUY_TM_ID;

    private String url;

    private String topcolor;

    private Map data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public void parseData(Map<String, String> map) {
        Map<String, Map> dataMap = new HashMap<>();

        Map<String, String> firstValueMap = new HashMap<>();
        firstValueMap.put("value", map.get("first"));
        firstValueMap.put("color", TemplateMsgConstant.PHONE_CHARGE_TM_COLOR);
        dataMap.put("first", firstValueMap);

        Map<String, String> keyword1ValueMap = new HashMap<>();
        keyword1ValueMap.put("value", map.get("keyword1"));
        keyword1ValueMap.put("color", TemplateMsgConstant.PHONE_CHARGE_TM_COLOR);
        dataMap.put("keyword1", keyword1ValueMap);

        Map<String, String> keyword2ValueMap = new HashMap<>();
        keyword2ValueMap.put("value", map.get("keyword2"));
        keyword2ValueMap.put("color", TemplateMsgConstant.PHONE_CHARGE_TM_COLOR);
        dataMap.put("keyword2", keyword2ValueMap);

        Map<String, String> remarkValueMap = new HashMap<>();
        remarkValueMap.put("value", map.get("remark"));
        remarkValueMap.put("color", TemplateMsgConstant.PHONE_CHARGE_TM_COLOR);
        dataMap.put("remark", remarkValueMap);

        this.data = dataMap;
    }

}

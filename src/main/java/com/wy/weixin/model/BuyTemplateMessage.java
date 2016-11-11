package com.wy.weixin.model;

import com.alibaba.fastjson.JSONObject;
import com.wy.weixin.constants.TemplateMsgConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 购买成功通知模板消息
 */

public class BuyTemplateMessage {

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

    /**
     * 将包含key-value的map解析成为data
     * @param templateValueMaps
     */
    public void parseData(Map<String, String> templateValueMaps) {
        Map<String, Map> dataMap = new HashMap<>();

        Map<String, String> titleValueMap = new HashMap<>();
        titleValueMap.put("value", templateValueMaps.get("title"));
        titleValueMap.put("color", TemplateMsgConstant.BUY_TM_COLOR);
        dataMap.put("title", titleValueMap);

        Map<String, String> productValueMap = new HashMap<>();
        productValueMap.put("value", templateValueMaps.get("product"));
        productValueMap.put("color", TemplateMsgConstant.BUY_TM_COLOR);
        dataMap.put("product", productValueMap);

        Map<String, String> priceValueMap = new HashMap<>();
        priceValueMap.put("value", templateValueMaps.get("price"));
        priceValueMap.put("color", TemplateMsgConstant.BUY_TM_COLOR);
        dataMap.put("price", priceValueMap);

        Map<String, String> timeValueMap = new HashMap<>();
        timeValueMap.put("value", templateValueMaps.get("time"));
        timeValueMap.put("color", TemplateMsgConstant.BUY_TM_COLOR);
        dataMap.put("time", timeValueMap);

        Map<String, String> remarkValueMap = new HashMap<>();
        remarkValueMap.put("value", templateValueMaps.get("remark"));
        remarkValueMap.put("color", TemplateMsgConstant.BUY_TM_COLOR);
        dataMap.put("remark", remarkValueMap);

        this.data = dataMap;
    }

}

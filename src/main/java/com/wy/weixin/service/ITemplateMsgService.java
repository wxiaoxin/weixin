package com.wy.weixin.service;

import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 模板消息接口
 */

public interface ITemplateMsgService {

    /**
     * 发送购买商品成功的模板消息
     * @param touser    用户id
     * @param data      封装数据
     */
    void sendBuyTMsg(String touser, Map<String, String> data);


    /**
     * 发送充值话费成功模板消息
     * @param touser
     * @param url
     * @param data
     */
    void sendPhoneChargeTMsg(String touser, String url, Map<String, String> data);

}

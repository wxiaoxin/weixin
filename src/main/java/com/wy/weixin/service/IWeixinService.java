package com.wy.weixin.service;

import com.wy.weixin.model.User;

import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

public interface IWeixinService {


    /**
     * 获取AccessToken
     * @return          access_token
     */
    String getToken();

    /**
     * 获取jsapi_ticket
     * @return          jsapi_ticket
     */
    String getJsApiTicket();

    /**
     * jsapi_ticket签名
     */
    Map<String, String> jsApiTicketSign(String url);

    /**
     * 获取网页授权Token
     * @param code      授权回调的code
     */
    Map<String, String> getWebAuthToken(String code);

    /**
     * 获取网页授权的用户信息
     * @param access_token      网页授权token
     * @param openid            用户openid
     * @return                  微信用户实例对象
     */
    User getWebAuthUserInfo(String access_token, String openid);

    /**
     * 获取网页授权的用户信息
     * @param map               包含了网页授权token和openid的参数
     * @return                  微信用户实例对象
     */
    User getWebAuthUserInfo(Map<String, String> map);

}

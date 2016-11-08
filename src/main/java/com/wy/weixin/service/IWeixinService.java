package com.wy.weixin.service;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信常用服务
 */

public interface IWeixinService {


    /**
     * 获取AccessToken
     * @param appid
     * @param appscreat
     * @return
     */
    String getToken();

}

package com.wy.weixin.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信配置
 */

public class WeixinConfigConstant {

    /**
     * 自定义微信TOKEN
     */
    @Value("${weixin.TOKEN}")
    public static String TOKEN;

    /**
     * 网页授权回调地址
     */
    @Value("${weixin.AUTH_REDIRECT_URI}")
    public static String AUTH_REDIRECT_URI;

    /**
     * 约惠MALL appid
     */
    @Value("${weixin.APPID}")
    public static String APPID;

    /**
     * 约惠MALL
     */
    @Value("${weixin.APPSECRET}")
    public static String APPSECRET;

    /**
     * 约惠MALL支付账号
     */
    @Value("${weixin.MCH_ID}")
    public static String MCH_ID;

    /**
     * 约惠MALL 支付scret
     */
    @Value("${weixin.PAY_SECRET}")
    public static String PAY_SECRET;

    /**
     * 约惠MALL 微信支付证书密钥，默认为MCH_ID
     */
    @Value("${weixin.KEYSTORE_PASSWORD}")
    public static String KEYSTORE_PASSWORD;

    /**
     * 约惠MALL 支付key
     */
    @Value("${weixin.PAY_KEY}")
    public static String PAY_KEY;


}

package com.wy.weixin.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信配置
 */

@Component
public class WeixinConfigConstant {

    /**
     * 自定义微信TOKEN
     */
    public static String TOKEN;

    /**
     * 网页授权回调地址
     */
    public static String AUTH_REDIRECT_URI;

    /**
     * 约惠MALL appid
     */
    public static String APPID;

    /**
     * 约惠MALL
     */
    public static String APPSECRET;

    /**
     * 约惠MALL支付账号
     */
    public static String MCH_ID;

    /**
     * 约惠MALL 支付scret
     */
    public static String PAY_SECRET;

    /**
     * 约惠MALL 微信支付证书密钥，默认为MCH_ID
     */
    public static String KEYSTORE_PASSWORD;

    /**
     * 约惠MALL 支付key
     */
    public static String PAY_KEY;


    @Value("${weixin.TOKEN}")
    public void setTOKEN(String TOKEN) {
        WeixinConfigConstant.TOKEN = TOKEN;
    }

    @Value("${weixin.AUTH_REDIRECT_URI}")
    public void setAuthRedirectUri(String authRedirectUri) {
        AUTH_REDIRECT_URI = authRedirectUri;
    }

    @Value("${weixin.APPID}")
    public void setAPPID(String APPID) {
        WeixinConfigConstant.APPID = APPID;
    }

    @Value("${weixin.APPSECRET}")
    public void setAPPSECRET(String APPSECRET) {
        WeixinConfigConstant.APPSECRET = APPSECRET;
    }

    @Value("${weixin.MCH_ID}")
    public void setMchId(String mchId) {
        MCH_ID = mchId;
    }

    @Value("${weixin.PAY_SECRET}")
    public void setPaySecret(String paySecret) {
        PAY_SECRET = paySecret;
    }

    @Value("${weixin.KEYSTORE_PASSWORD}")
    public void setKeystorePassword(String keystorePassword) {
        KEYSTORE_PASSWORD = keystorePassword;
    }

    @Value("${weixin.PAY_KEY}")
    public void setPayKey(String payKey) {
        PAY_KEY = payKey;
    }
}

package com.wy.weixin.constants;

/**
 * Created by wxiao on 2016.11.9.
 *
 * 微信各请求地址常量
 */

public class WeixinUrlConstants {

    /**
     * 网页授权回调地址
     * 赋值：APPID REDIRECT_URI STATE
     */
    public static String WEBAUTH_REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

    /**
     * 获取Token请求地址
     * 赋值：APPID APPSECRET
     */
    public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取用户基本信息请求地址
     * 赋值：ACCESS_TOKEN OPENID
     */
    public static String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取网页授权Token请求地址
     * 赋值：APPID APPSECRET CODE
     */
    public static String GET_WEBAUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

    /**
     * 网页授权后拉取用户信息的请求地址
     * 赋值：ACCSS_TOKEN OPENID
     */
    public static String GET_WEBAUTH_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 发送模板消息请求地址
     * 赋值：ACCESS_TOKEN
     */
    public static String POST_SEND_TM_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";




}

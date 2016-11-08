package com.wy.weixin.constants;

/**
 * Created by wxiao on 2016.11.9.
 *
 * 微信各请求地址常量
 */

public class WeixinUrlConstants {


    /**
     * 获取Access_TOKEN
     */
    public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 发送模板消息请求接口
     */
    public static String POST_SEND_TM_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


}

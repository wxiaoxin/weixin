package com.wy.weixin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信核心服务接口
 */

public interface IWeixinCoreService {

    /**
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。
     * 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。
     *
     * 加密/校验流程如下：
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */
    boolean checkSignature(HttpServletRequest request);

    /**
     * 接收和处理微信消息
     * @param request       携带有微信消息的Http请求
     * @param response      回复给微信的http响应
     */
    String handleMsg(HttpServletRequest request, HttpServletResponse response);

}

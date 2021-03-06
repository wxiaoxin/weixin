package com.wy.weixin.service.impl;

import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinMessageConstant;
import com.wy.weixin.constants.WeixinUrlConstants;
import com.wy.weixin.model.TextMessage;
import com.wy.weixin.service.ITemplateMsgService;
import com.wy.weixin.service.IWeixinCoreService;
import com.wy.weixin.util.Utils;
import com.wy.weixin.util.XMLUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信开发主服务
 */

@Service
public class WeixinCoreService implements IWeixinCoreService {


    /*模板消息服务*/
    @Autowired
    private ITemplateMsgService templateMsgService;

    public boolean checkSignature(HttpServletRequest request) {
        // signature 微信加密签名
        String signature = request.getParameter("signature");
        // timestamp 时间戳
        String timestamp = request.getParameter("timestamp");
        // nonce 随机数
        String nonce = request.getParameter("nonce");
        // echostr 随机数
        String echostr = request.getParameter("echostr");

        // 字典排序
        String[] arr = new String[]{WeixinConfigConstant.TOKEN, timestamp, nonce};
        Arrays.sort(arr);

        // SHA1加密
        StringBuilder sbf = new StringBuilder();
        for (String s : arr) {
            sbf.append(s);
        }
        String s = Utils.SHA1(sbf.toString());

        // 判断加密后字符串与请求中的字符串是否相等，并返回结果
        return s.equals(signature);
    }

    @Override
    public String handleMsg(HttpServletRequest request, HttpServletResponse response) {
        String result = "";
        try {
            ServletInputStream is = request.getInputStream();
            // 解析得到消息的map格式
            Map<String, String> msgMap = XMLUtil.xmlISToMap(is);
            String toUserName = msgMap.get("ToUserName");
            String fromUserName = msgMap.get("FromUserName");
            String createTime = msgMap.get("CreateTime");
            String msgType = msgMap.get("MsgType");
            String msgId = msgMap.get("MsgId");


            // 响应文本消息
            TextMessage message = new TextMessage();
            message.setToUserName(fromUserName);
            message.setFromUserName(toUserName);
            message.setCreateTime(String.valueOf(new Date().getTime()));
            message.setMsgType(WeixinMessageConstant.TYPE_TEXT);

            // 文本消息
            if(msgType.equals(WeixinMessageConstant.TYPE_TEXT)) {
                String content = msgMap.get("Content");

                if(content.equals("模板")) {
                    Map<String,String> map = new HashMap<>();
                    map.put("first", "话费充值提醒");
                    map.put("keyword1", "15221505770");
                    map.put("keyword2", "100");
                    map.put("remark", "感谢使用在线充值！");

                    templateMsgService.sendPhoneChargeTMsg(fromUserName, "http://www.baidu.com", map);
                    message.setContenxt("已发送模板消息");
                } else if(content.equals("授权")) {
                    String url = WeixinUrlConstants.WEBAUTH_REDIRECT_URL
                                    .replace("APPID", WeixinConfigConstant.APPID)
                                    .replace("REDIRECT_URI", WeixinConfigConstant.AUTH_REDIRECT_URI)
                                    .replace("STATE", "123");
                    String respContent = "<a href=\"" + url + "\">授权</a>";
                    message.setContenxt(respContent);
                } else {
                    message.setContenxt(content);
                }
            }

            if(msgType.equals(WeixinMessageConstant.TYPE_EVENT)) {
                String event = msgMap.get("Event");
                if(event.equals(WeixinMessageConstant.EVENT_TYPE_SUBSCRIBE)) {
                    // 订阅事件
                    message.setContenxt("感谢关注！");
                }
            }

            result = XMLUtil.textMsgToXml(message);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 处理文本消息
     *
     * @param content 用户发送的文本消息内容
     * @return
     */
    private String handleTextMsg(String content) {
        String respContent = "";

        // TODO 业务逻辑
        if (content.equals("模板")) {
            // 回复模板消息


        } else {
            respContent = content;
        }
        return respContent;
    }

}

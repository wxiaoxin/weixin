package com.wy.weixin.service.impl;

import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.constants.WeixinMessageConstant;
import com.wy.weixin.model.TextMessage;
import com.wy.weixin.service.IWeixinCoreService;
import com.wy.weixin.util.XMLUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信开发主服务
 */

@Service
public class WeixinCoreService implements IWeixinCoreService {

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
        for(String s : arr) {
            sbf.append(s);
        }
        String s = SHA1(sbf.toString());

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

            switch (msgType) {

                // 文本类型消息
                case WeixinMessageConstant.TYPE_TEXT:
                    // 文本内容
                    String content = msgMap.get("Content");
                    // 返回相同的内容
                    message.setContenxt(content);
                    result = XMLUtil.textMsgToXml(message);
                    break;

                // 图片类型消息
                case WeixinMessageConstant.TYPE_IMAGE:
                    break;

                default:

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * SHA1加密算法
     *
     * @param decript   待加密的字符串
     * @return          加密后的字符串
     */
    private static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

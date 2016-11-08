package com.wy.weixin.model;

import com.wy.weixin.constants.WeixinMessageConstant;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 文本消息
 */

public class TextMessage extends BaseMessage {

    // 文本消息类型
    private String MsgType = WeixinMessageConstant.TYPE_TEXT;

    // 文本消息内容
    private String Content;

    public TextMessage() {
    }

    @Override
    public String getMsgType() {
        return MsgType;
    }

    @Override
    public void setMsgType(String msgType) {
        this.MsgType = msgType;
    }

    public String getContenxt() {
        return Content;
    }

    public void setContenxt(String content) {
        this.Content = content;
    }
}

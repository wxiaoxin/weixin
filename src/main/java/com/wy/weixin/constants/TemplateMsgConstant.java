package com.wy.weixin.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 模板消息相关常量
 */

public class TemplateMsgConstant {

    /**
     * 购买通知模板消息ID
     */
    @Value("tempMsg.BUY_TM_ID")
    public static String BUY_TM_ID;

    /**
     * 购买通知模板消息的字体颜色
     */
    @Value("tempMsg.BUY_TM_COLOR")
    public static String BUY_TM_COLOR;

    /**
     * 话费充值通知模板消息ID
     */
    @Value("${tempMsg.PHONE_CHARGE_TM_ID}")
    public static String PHONE_CHARGE_TM_ID;

    /**
     * 话费充值通知模板消息的字体颜色
     */
    @Value("${tempMsg.PHONE_CHARGE_TM_COLOR}")
    public static String PHONE_CHARGE_TM_COLOR;

}

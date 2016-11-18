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
    public static String BUY_TM_ID;

    /**
     * 购买通知模板消息的字体颜色
     */
    public static String BUY_TM_COLOR;

    /**
     * 话费充值通知模板消息ID
     */
    public static String PHONE_CHARGE_TM_ID;

    /**
     * 话费充值通知模板消息的字体颜色
     */
    public static String PHONE_CHARGE_TM_COLOR;


    @Value("tempMsg.BUY_TM_ID")
    public void setBuyTmId(String buyTmId) {
        BUY_TM_ID = buyTmId;
    }

    @Value("tempMsg.BUY_TM_COLOR")
    public static void setBuyTmColor(String buyTmColor) {
        BUY_TM_COLOR = buyTmColor;
    }

    @Value("${tempMsg.PHONE_CHARGE_TM_ID}")
    public static void setPhoneChargeTmId(String phoneChargeTmId) {
        PHONE_CHARGE_TM_ID = phoneChargeTmId;
    }

    @Value("${tempMsg.PHONE_CHARGE_TM_COLOR}")
    public static void setPhoneChargeTmColor(String phoneChargeTmColor) {
        PHONE_CHARGE_TM_COLOR = phoneChargeTmColor;
    }
}

package com.wy.weixin.service.impl;

import com.wy.weixin.BaseTest;
import com.wy.weixin.service.IWeixinService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wxiao on 2016/11/11.
 *
 * 微信服务测试
 */

public class WeixinServiceTest extends BaseTest {

    @Autowired
    private IWeixinService weixinService;


    @Test
    public void getToken() throws Exception {
        String token = weixinService.getToken();
        System.out.println(token);
    }

}
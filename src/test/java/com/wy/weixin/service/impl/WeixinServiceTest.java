package com.wy.weixin.service.impl;

import com.wy.weixin.service.IWeixinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016/11/16.
 */

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WeixinServiceTest {

    @Autowired
    private IWeixinService weixinService;

    @Test
    public void getToken() throws Exception {

    }

    @Test
    public void getJsApiTicket() throws Exception {

    }

    @Test
    public void jsApiTicketSign() throws Exception {

    }

    @Test
    public void paySign() throws Exception {

    }

    @Test
    public void getWebAuthToken() throws Exception {

    }

    @Test
    public void getWebAuthUserInfo() throws Exception {

    }

    @Test
    public void getWebAuthUserInfo1() throws Exception {

    }

}
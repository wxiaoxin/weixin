package com.wy.weixin.constants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wxiao on 2016/11/17.
 */

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class WeixinConfigConstantTest {

    @Test
    public void test() {
        System.out.println(WeixinConfigConstant.APPID);
        System.out.println(WeixinConfigConstant.APPSECRET);
    }
    
}
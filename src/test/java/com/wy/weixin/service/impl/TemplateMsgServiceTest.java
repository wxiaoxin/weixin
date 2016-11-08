package com.wy.weixin.service.impl;

import com.wy.weixin.service.ITemplateMsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wxiao on 2016.11.9.
 *
 * 模板服务测试类
 */

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TemplateMsgServiceTest {

    @Autowired
    private ITemplateMsgService service;

    @Test
    public void sendMsg() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("title", "京东付款成功通知");
        map.put("product", "高级程序设计");
        map.put("price", "12.34");
        map.put("time", "2016-11-09");
        map.put("remark", "我们即将为您发货！祝您生活愉快！");

        service.sendMsg("osZDJt8-g0HUflpZQeeFFIQMw17w", map);
    }

}
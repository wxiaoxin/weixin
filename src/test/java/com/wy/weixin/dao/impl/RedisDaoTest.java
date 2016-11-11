package com.wy.weixin.dao.impl;

import com.wy.weixin.dao.IRedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by wxiao on 2016/11/11.
 *
 * Redis操作测试类
 */

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisDaoTest {

    @Autowired
    private IRedisDao redisDao;

    @Test
    public void set() throws Exception {
        redisDao.set("name", "wxiaoxin");
    }

    @Test
    public void set2() {
        redisDao.set("token", "123", 15);
    }

    @Test
    public void get() {
        String name = redisDao.get("token");
        System.out.println(name);
    }

}
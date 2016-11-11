package com.wy.weixin.dao.impl;

import com.wy.weixin.dao.IRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by wxiao on 2016/11/11.
 *
 * Redis操作类
 */

@Service
public class RedisDao extends BaseDao implements IRedisDao {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(key, value);
    }

    @Override
    public void set(String key, String value, long expires) {
        ValueOperations valueOp = redisTemplate.opsForValue();
        valueOp.set(key, value, expires, TimeUnit.MINUTES);
    }

    @Override
    public String get(String key) {
        ValueOperations valueOp = redisTemplate.opsForValue();
        return (String) valueOp.get(key);
    }
}

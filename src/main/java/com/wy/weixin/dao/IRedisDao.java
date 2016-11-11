package com.wy.weixin.dao;

/**
 * Created by wxiao on 2016/11/11.
 *
 * Redis操作类接口
 */

public interface IRedisDao {

    void set(String key, String value);

    void set(String key, String value, long expires);

    String get(String key);

}

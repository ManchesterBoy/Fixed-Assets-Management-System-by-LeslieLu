package com.example.sboot.common;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 删除Redis里面的缓存功能的封装
 * @author LeslieLu
 */

@Component
public class RedisClearCache {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //执行完增删改操作后，需删除Redis里面的缓存
    public void flushRedis(String key){
        stringRedisTemplate.delete(key);
    }
}

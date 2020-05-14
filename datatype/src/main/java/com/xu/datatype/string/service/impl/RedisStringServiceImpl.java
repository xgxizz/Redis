package com.xu.datatype.string.service.impl;

import com.xu.datatype.config.RedisConfig;
import com.xu.datatype.string.service.RedisStringService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/17 18:45
 */
@Service
public class RedisStringServiceImpl implements RedisStringService {
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public int set(String keyName, String keyValue) {
        redisConfig.valueOperations(redisTemplate).set(keyName,keyValue);
        return 1;
    }

    @Override
    public boolean delete(String str) {
        return redisTemplate.delete(str);
    }

    @Override
    public int modify(String key, String value) {
        try {
            redisConfig.valueOperations(redisTemplate).set(key, value);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public String query(String str) {
        return (String) redisConfig.valueOperations(redisTemplate).get(str);
    }

    @Override
    public boolean setTimeout(String keyName,long timeout) {
        return redisTemplate.expire(keyName,timeout , TimeUnit.MILLISECONDS);
    }
}

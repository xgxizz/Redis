package com.xu.datatype.hash.service.impl;

import com.xu.datatype.config.RedisConfig;
import com.xu.datatype.hash.entity.Student;
import com.xu.datatype.hash.service.RedisHashService;
import com.xu.datatype.hash.util.BeanMapUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 14:47
 */
@Service
public class RedisHashServiceImpl implements RedisHashService {
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public int set(Student student) {

       //多个field写入
       try {
           Map map = BeanMapUtil.objectToMap(student);
           redisConfig.hashOperations(redisTemplate).putAll("student", map);
       }catch (Exception e){
           e.printStackTrace();
       }
       return 1;
    }

    @Override
    public Object queryOneHashField(String keyName, String fieldName) {
        return redisConfig.hashOperations(redisTemplate).get(keyName,fieldName);
    }

    @Override
    public List queryMutiHashFields(String keyName, Collection<String> collection) {
        return redisConfig.hashOperations(redisTemplate).multiGet(keyName,collection);
    }

    @Override
    public int deleteFields(String keyName, String... fieldNames) {
        return redisConfig.hashOperations(redisTemplate).delete(keyName,fieldNames).intValue();
    }
}

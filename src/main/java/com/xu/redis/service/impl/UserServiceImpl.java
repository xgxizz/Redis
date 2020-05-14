package com.xu.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xu.redis.config.RedisKeyConfig;
import com.xu.redis.config.RedisObjectSerializer;
import com.xu.redis.dao.UserDao;
import com.xu.redis.domain.UserDO;
import com.xu.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgx
 * @Date 2019/8/20 14:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    @Override
    public List findAllUsers() {
        if (!redisTemplate.hasKey(RedisKeyConfig.UserList)) {
            List<UserDO> userList = userDao.findAll();
            System.out.println("当前时间"+new Date()+",读取Mysql中的内容：" +userList);
            for (int i = 0; i < userList.size(); i++) {
                redisTemplate.opsForList().rightPush(RedisKeyConfig.UserList,userList.get(i));
            }
            redisTemplate.expire(RedisKeyConfig.UserList,1, TimeUnit.MINUTES);
            System.out.println("当前时间"+new Date()+",已经将列表写入缓存，并且已经设置了缓存失效");
            return userList;
        }else{
            System.out.println("当前时间"+new Date()+",读取redis中的内容：" +JSONObject.toJSONString(redisTemplate.opsForList().range(RedisKeyConfig.UserList,0,-1)));
            return redisTemplate.opsForList().range(RedisKeyConfig.UserList,0,-1);
        }
    }

    @Override
    public UserDO updateUser() {

        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setAccount("testThread"+ UUID.randomUUID().toString());
        userDO = userDao.save(userDO);
        System.out.println("当前时间"+new Date() +",用户列表有更新");
        //设置缓存失效
        redisTemplate.expire(RedisKeyConfig.UserList,0, TimeUnit.MINUTES);
        return userDO;
    }
}

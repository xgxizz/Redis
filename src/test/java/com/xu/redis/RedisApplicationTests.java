package com.xu.redis;

import com.xu.redis.service.UserService;
import com.xu.redis.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Resource
    private UserService userService;
    @Test
    public void contextLoads() {
        System.out.println(userService.getClass() == UserServiceImpl.class);
    }

}

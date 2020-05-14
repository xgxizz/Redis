package com.xu.datatype.string;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 16:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisStringTest {

    @Resource(name = "StringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    //插入字符串
    public void setAndGet(){
        redisTemplate.opsForValue().set("name", "abc");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    //删除key
    @Test
    public void delete(){
        redisTemplate.delete("name");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    //设置过期时间
    @Test
    public void expire(){
        //第一种方式
        //redisTemplate.opsForValue().set("expire","expire",10, TimeUnit.SECONDS);
        //第二种方式
        redisTemplate.opsForValue().set("expire","expire");
        redisTemplate.expire("expire", 10, TimeUnit.SECONDS);

        for (int i = 0; i < 15; i++) {
            System.out.println(redisTemplate.opsForValue().get("expire"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

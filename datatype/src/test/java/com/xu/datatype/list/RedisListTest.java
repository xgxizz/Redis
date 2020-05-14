package com.xu.datatype.list;

import com.xu.datatype.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 17:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisListTest {

    @Resource
    private RedisConfig redisConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void push(){
        redisConfig.listOperations(redisTemplate).leftPush("myList","a");
        redisConfig.listOperations(redisTemplate).leftPushAll("myList",new String[]{"b","c","d"});
        System.out.println("写入List数据成功");
    }

    @Test
    public void pushOneElement(){
        redisConfig.listOperations(redisTemplate).leftPush("myList","b");
    }
    @Test
    public void leftPop(){
        redisConfig.listOperations(redisTemplate).leftPush("myList","a");
        log.info("第一个元素是：" + redisConfig.listOperations(redisTemplate).leftPop("myList"));
        log.info("第一个元素是：" + redisConfig.listOperations(redisTemplate).leftPop("myList",30, TimeUnit.SECONDS));
    }
    @Test
    public void clear(){
        long len = redisConfig.listOperations(redisTemplate).size("myList");
        for (int i = 0; i < len; i++) {
            redisConfig.listOperations(redisTemplate).leftPop("myList");
        }
    }
    @Test
    public void remove(){
        //删除
        redisTemplate.delete("myList");
        //Push进入4个‘c’
        redisConfig.listOperations(redisTemplate).leftPushAll("myList",new String[]{"a","c","c","c","c","b"});
        log.info("没有进行左移除时的数据："+redisConfig.listOperations(redisTemplate).range("myList",0,100));
        //从左移除一个
        redisConfig.listOperations(redisTemplate).remove("myList",1,"c");//从左侧移除1个c
        log.info("左移除之后的数据："+redisConfig.listOperations(redisTemplate).range("myList",0,100));
        redisConfig.listOperations(redisTemplate).remove("myList",2,"c");//从右侧移除2个c
        log.info("右移除之后的数据："+redisConfig.listOperations(redisTemplate).range("myList",0,100));
        /*没有进行左移除时的数据：[b, c, c, c, c, a]
        左移除之后的数据：[b, c, c, c, a]
        右移除之后的数据：[b, c, a]*/
    }
    @Test
    public void rightPopAndRightPush(){
        redisConfig.listOperations(redisTemplate).rightPopAndLeftPush("myList","mylist");
        System.out.println("右侧Pop左侧push完成");
    }
    //展示元素
    @Test
    public void show(){
        List list = redisConfig.listOperations(redisTemplate).range("myList",0,redisConfig.listOperations(redisTemplate).size("myList"));
        System.out.println(list);
    }
    @Test
    public void getLength(){
        int length = redisConfig.listOperations(redisTemplate).size("myList").intValue();
        System.out.println("myList的长度是"+length);
    }

    @Test
    public void lrem(){
        redisTemplate.opsForList().remove("myList", 0, "c");
    }
}

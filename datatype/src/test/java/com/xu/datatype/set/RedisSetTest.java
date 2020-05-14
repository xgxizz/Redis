package com.xu.datatype.set;

import com.xu.datatype.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/21 15:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisSetTest {
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void add(){
        redisConfig.setOperations(redisTemplate).add("mySet",new String[]{"java","python","go"});
        log.info("Set add finish");
    }
    @Test
    public void getSetSize(){
        int size = redisConfig.setOperations(redisTemplate).size("mySet").intValue();
        System.out.println(size);
    }

    @Test
    public void diff(){
        redisConfig.setOperations(redisTemplate).add("set01", new String[]{"java","python","go"});
        redisConfig.setOperations(redisTemplate).add("set02", new String[]{"java", "php"});
        redisConfig.setOperations(redisTemplate).add("set03", new String[]{"java","R","C++"});
        Set set = redisConfig.setOperations(redisTemplate).difference("set01", "set02");
        System.out.println(redisConfig.setOperations(redisTemplate).differenceAndStore("set01","set02","set03"));
    }

    @Test
    public void showMember(){
        Set set01 = redisConfig.setOperations(redisTemplate).members("set01");
        System.out.println(set01);
    }

    //交集
    @Test
    public void inter(){
        Set set = redisConfig.setOperations(redisTemplate).intersect("set01","set02");
        System.out.println("交集是："+ set);
    }

    @Test
    public void isMember(){
        boolean flag = redisConfig.setOperations(redisTemplate).isMember("set01","java");
        System.out.println(flag);
    }

    //返回随机值 随即返回的可能有相同的值
    @Test
    public void random(){
        List<Object> list = redisConfig.setOperations(redisTemplate).randomMembers("set01",2);
        System.out.println(list);
    }

    //随机移除并返回移除的数据
    @Test
    public void pop(){
        Object object = redisConfig.setOperations(redisTemplate).pop("set03");
        System.out.println(object);
    }

    //remove
    @Test
    public void remove(){
        int num = redisConfig.setOperations(redisTemplate).remove("set1","java").intValue();
        System.out.println("成功移除了："+ num + "个元素");
    }

    @Test
    public void union(){
        Set set = redisConfig.setOperations(redisTemplate).union("set01","set02");
        System.out.println(set);
    }

    //求并集，写入另一个key
    @Test
    public void unionToDest(){
        long num  = redisConfig.setOperations(redisTemplate).unionAndStore("set01","set02","set04");
    }
    //迭代
    @Test
    public void scan(){
        Cursor<Object> cursor = redisConfig.setOperations(redisTemplate).scan("set01", new ScanOptions.ScanOptionsBuilder().match("j*").build());
        while (cursor.hasNext()){
            System.out.println("-------->"+cursor.next());
        }
    }
}

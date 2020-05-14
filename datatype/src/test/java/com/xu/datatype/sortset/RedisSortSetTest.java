package com.xu.datatype.sortset;

import com.xu.datatype.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/27 11:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisSortSetTest {
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void add(){
        redisConfig.zSetOperations(redisTemplate).add("subjectScore","java",1);
        redisConfig.zSetOperations(redisTemplate).add("subjectScore","python",5);
        redisConfig.zSetOperations(redisTemplate).add("subjectScore","C++",3);
    }
    @Test
    public void query(){
        long size = redisConfig.zSetOperations(redisTemplate).size("subjectScore");
        Set set = redisConfig.zSetOperations(redisTemplate).range("subjectScore",0,size);
        System.out.println(set);
    }
    //查看指定区间的成员数
    @Test
    public void excount(){
        long excount = redisConfig.zSetOperations(redisTemplate).count("subjectScore",2,5);
        System.out.println(excount);
    }
    //某成员添加增量
    @Test
    public void increby(){
        redisConfig.zSetOperations(redisTemplate).incrementScore("subjectScore","java",5);
    }

    @Test
    public void interStore(){
        redisConfig.zSetOperations(redisTemplate).add("subjectScore2","java",5);
        redisConfig.zSetOperations(redisTemplate).add("subjectScore2","C#",20);
        redisConfig.zSetOperations(redisTemplate).intersectAndStore("subjectScore","subjectScore2","subjectScoreInter");
    }

    //查询集合元素的总数
    @Test
    public void count(){
        long sum = redisConfig.zSetOperations(redisTemplate).size("subjectScore");
        System.out.println("subjectScore一共含有"+sum+"个元素");
    }

    //Redis Zrangebylex 通过字典区间返回有序集合的成员。
    @Test
    public void rangebylex(){
        Set<ZSetOperations.TypedTuple<Object>> sets = new HashSet<>();
        sets.add(new DefaultTypedTuple("a",0.0));
        sets.add(new DefaultTypedTuple("b",0.0));
        sets.add(new DefaultTypedTuple("c",0.0));
        sets.add(new DefaultTypedTuple("d",0.0));
        sets.add(new DefaultTypedTuple("e",0.0));
        redisConfig.zSetOperations(redisTemplate).add("mySortSet",sets);

        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.lt("c");
        //用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定。
        Set set = redisConfig.zSetOperations(redisTemplate).rangeByLex("mySortSet", range);
        System.out.println(set);
        //输出结果b、c、d
    }
}

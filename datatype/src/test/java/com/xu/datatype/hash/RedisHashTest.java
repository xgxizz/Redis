package com.xu.datatype.hash;

import com.xu.datatype.hash.entity.Student;
import com.xu.datatype.hash.service.RedisHashService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 16:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisHashTest {
    static List<String> fields = new ArrayList<>();
    static {
        fields.add("name");
        fields.add("age");
        fields.add("gender");
    }
    @Resource
    private RedisHashService redisHashService;

    @Test
    public void set() throws IllegalAccessException {
        Student student = new Student("xgx",23,"man");
        redisHashService.set(student);
        log.info("写入完成，写入后的数据是：");
        log.info(redisHashService.queryMutiHashFields("student",fields).toString());
    }

    @Test
    public void deleteFields(){
        int num = redisHashService.deleteFields("student",new String[]{"gender"});
        log.info("已删除"+ num + "个字段");
        log.info(redisHashService.queryMutiHashFields("student",fields).toString());
    }

    @Test
    public void queryOneHashField(){
        log.info("student 的 age 为："+redisHashService.queryOneHashField("student","age"));
    }
}

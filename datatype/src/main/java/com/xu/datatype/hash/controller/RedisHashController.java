package com.xu.datatype.hash.controller;

import com.xu.datatype.hash.entity.Student;
import com.xu.datatype.hash.service.RedisHashService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 14:18
 */
@RestController
@RequestMapping("redis/hash")
public class RedisHashController {

    @Resource
    private RedisHashService redisHashService;
    @RequestMapping("/set")
    public String set(@RequestBody(required = false)Student student) throws IllegalAccessException {
        int flag = redisHashService.set(student);
        if (flag == 1)
            return "写入Hash成功";
        else
            return "写入Hash失败";
    }
    @RequestMapping("/queryOneHashField")
    public Object queryOneHashField(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        String fieldName = (String) map.get("fieldName");
        return redisHashService.queryOneHashField(keyName, fieldName);
    }

    @RequestMapping("/queryMutiHashFields")
    public List queryMutiHashFields(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        List<String> fields = (List<String>) map.get("fields");
        return redisHashService.queryMutiHashFields(keyName,fields);
    }
}

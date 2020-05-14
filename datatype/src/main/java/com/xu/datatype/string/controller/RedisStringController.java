package com.xu.datatype.string.controller;

import com.xu.datatype.string.service.RedisStringService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/17 18:34
 */
@RestController
@RequestMapping("/redis/string")
public class RedisStringController {

    @Resource
    private RedisStringService redisStringService;
    @RequestMapping("/add")
    public String add(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        String keyValue = (String) map.get("keyValue");
        redisStringService.set(keyName,keyValue);
        return "写入完成！！";
    }

    @RequestMapping("/query")
    public String query(@RequestBody Map map){
        String key = (String) map.get("keyName");
        return redisStringService.query(key);
    }

    @RequestMapping("/modify")
    public String modify(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        String keyValue = (String) map.get("keyValue");
        int flag = redisStringService.modify(keyName,keyValue);
        if (flag > 0 ){
            return "修改成功";
        }else{
            return "修改失败";
        }
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        boolean flag = redisStringService.delete(keyName);
        if (flag == true ){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    @RequestMapping("/setTimeOut")
    public String setTimeOut(@RequestBody Map map){
        String keyName = (String) map.get("keyName");
        boolean flag = redisStringService.setTimeout(keyName,10000);
        if (flag == true){
            return "设置过期时间成功";
        }else
            return "设置过期时间失败";
    }


}

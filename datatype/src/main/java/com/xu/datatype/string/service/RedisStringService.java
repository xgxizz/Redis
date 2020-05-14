package com.xu.datatype.string.service;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/17 18:39
 */
public interface RedisStringService {

    //写入
    int set(String keyName, String keyValue);
    //删除
    boolean delete(String str);
    //修改
    int modify(String key,String value);
    //查询
    String query(String str);
    //修改过期时间
    boolean setTimeout(String keyName, long timeout);

}

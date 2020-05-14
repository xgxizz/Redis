package com.xu.datatype.hash.service;

import com.xu.datatype.hash.entity.Student;

import java.util.Collection;
import java.util.List;

public interface RedisHashService {

    int set(Student student) throws IllegalAccessException;

    //查询Hash中的一个字段的值
    Object queryOneHashField(String keyName, String fieldName);

    //查询Hash中的多个字段的值
    List queryMutiHashFields(String keyName, Collection<String> collection);

    //删除Hash中的一个字段
    int deleteFields(String keyName, String... fieldNames);

    //修改即为set,重新set一下就更新了。
}

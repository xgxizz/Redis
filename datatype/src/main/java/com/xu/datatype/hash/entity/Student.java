package com.xu.datatype.hash.entity;

import lombok.Data;

/**
 * @Description
 * @Author xgx
 * @Date 2019/9/19 14:44
 */
@Data
public class Student {
    String name;
    int age;
    String gender;

    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public Student(){

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}

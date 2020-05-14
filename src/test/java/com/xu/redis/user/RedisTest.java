package com.xu.redis.user;

import com.xu.redis.util.HttpUtil;

/**
 * @Description
 * @Author xgx
 * @Date 2019/8/20 18:48
 */
public class RedisTest {

    public static void main(String[] args) {
        while (true) {
            String str = HttpUtil.callWebPage("http://192.168.50.111:8080/allUser", null);
            System.out.println(str);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
package com.xu.redis.service;


import com.xu.redis.domain.UserDO;

import java.util.List;

/**
 * @Description
 * @Author xgx
 * @Date 2019/8/20 14:42
 */
public interface UserService {

    List findAllUsers();

    UserDO updateUser();
}

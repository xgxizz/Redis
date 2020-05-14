package com.xu.redis.controller;

import com.xu.redis.domain.UserDO;
import com.xu.redis.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author xgx
 * @Date 2019/8/20 14:50
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/allUser")
    public List getUserList(){
        return userService.findAllUsers();
    }

    @RequestMapping("/write")
    public UserDO write(){
        return userService.updateUser();
    }
}

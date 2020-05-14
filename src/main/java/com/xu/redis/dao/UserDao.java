package com.xu.redis.dao;

import com.xu.redis.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description
 * @Author xgx
 * @Date 2019/8/20 14:06
 */
public interface UserDao extends JpaRepository<UserDO , Long> {

}

package com.whl.demo.mapper;

import com.whl.demo.entity.User;

import java.util.List;

/**
 * @program: cache-redis
 * @description:
 * @author: Mr.Wang
 * @create: 2018-09-21 09:38
 **/
public interface UserMapper {

    public List<User> findAll();
}

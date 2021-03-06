package com.whl.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.whl.demo.entity.User;
import com.whl.demo.mapper.UserMapper;
import com.whl.demo.template.CacheLoadable;
import com.whl.demo.template.CacheTemplate;
import com.whl.demo.util.RedisUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: cache-redis
 * @description:
 * @author: Mr.Wang
 * @create: 2018-09-21 10:15
 **/
@Component
public class UserService {

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserMapper userMapper;

    @Resource
    CacheTemplate cacheTemplate;


    public   List<User>  getAllUser3()
    {
        Object obj = redisUtils.get("user");
        if(!StringUtils.isEmpty(obj) && !obj.toString().toLowerCase().equals("null"))
        {
            System.out.println("==================query cache===================");
            return JSONArray.parseArray(obj.toString(),User.class);
        }
        List<User> userList = null;
        System.out.println("==================query db===================");
        userList = userMapper.findAll();
        redisUtils.set("user", JSON.toJSON(userList),10, TimeUnit.MINUTES);
        return userList;
    }

    public List<User> getAllUser()
    {
       Object obj = redisUtils.get("user");
       if(!StringUtils.isEmpty(obj) && !obj.toString().toLowerCase().equals("null"))
       {
           System.out.println("==================query cache===================");
           return JSONArray.parseArray(obj.toString(),User.class);
       }

        List<User> userList = null;
       synchronized (this)
       {
           obj = redisUtils.get("user");
           if(!StringUtils.isEmpty(obj) && !obj.toString().toLowerCase().equals("null"))
           {
               System.out.println("==================query cache===================");
               return JSONArray.parseArray(obj.toString(),User.class);
           }

           System.out.println("==================query db===================");
           userList = userMapper.findAll();
           redisUtils.set("user", JSON.toJSON(userList),10, TimeUnit.MINUTES);
       }
       return userList;
    }

    public List<User> quertyForTemplate(){
        return cacheTemplate.findCache("user", 10, TimeUnit.MINUTES, new TypeReference<List<User>>(){}, new CacheLoadable() {
            @Override
            public Object load() {
                return userMapper.findAll();
            }
        });
    }

    public void telPhoneCount(String smsPhone){
        String redisKey = "SMS_LIMIT_" + smsPhone;
        long count = redisUtils.increment(redisKey, 1);
        if (count == 1) {
            //设置有效期一分钟
            redisUtils.expire(redisKey, 30, TimeUnit.SECONDS);
        }
        if (count > 1) {
            System.out.println("每30秒只能发送一次短信");
            return;
        }
        System.out.println("============开始发送短信==============");
    }

}

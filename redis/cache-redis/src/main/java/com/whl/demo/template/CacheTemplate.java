package com.whl.demo.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.whl.demo.entity.User;
import com.whl.demo.util.RedisUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: cache-redis
 * @description:
 * @author: Mr.Wang
 * @create: 2018-09-21 10:59
 **/
@Component
public class CacheTemplate {

    @Resource
    RedisUtils redisUtils;

    public <T> T findCache(String key, long expire, TimeUnit unit,
                         TypeReference<T> clazz, CacheLoadable cacheLoadable){

        String obj = String.valueOf(redisUtils.get(key));
        if(!StringUtils.isEmpty(obj) && !obj.toString().toLowerCase().equals("null"))
        {
            System.out.println("==================query cache===================");
            return JSON.parseObject(obj,clazz);
        }

        List<User> userList = null;
        synchronized (this)
        {
            obj = String.valueOf(redisUtils.get(key));
            if(!StringUtils.isEmpty(obj) && !obj.toString().toLowerCase().equals("null"))
            {
                System.out.println("==================query cache===================");
                return JSON.parseObject(obj,clazz);
            }

            System.out.println("==================query db===================");
            //userList = userMapper.findAll();cacheLoadable
            T result = (T) cacheLoadable.load();
            redisUtils.set(key, JSON.toJSON(result),expire, unit);
            return result;
        }
    }
}

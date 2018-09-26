package com.whl.demo.template;

/**
 * @program: cache-redis
 * @description:
 * @author: Mr.Wang
 * @create: 2018-09-21 11:01
 **/
public interface CacheLoadable<T> {

    T load();
}

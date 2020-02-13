package com.whl.demo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by steven on 2018/6/1.
 */
public class RedisUtils {
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置redis模板
	 * 
	 * @param redisTemplate
	 *            redis模板
	 */
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true:成功 false:失败
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param l
	 *            超时时间
	 * @return true:成功 false:失败
	 */
	public boolean set(String key, Object value, long l) {
		try {
			redisTemplate.opsForValue().set(key, value, l);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param l
	 *            超时时间
	 * @param unit
	 *            时间单元类
	 * @return true:成功 false:失败
	 */
	public boolean set(String key, Object value, long l, TimeUnit unit) {
		try {
			redisTemplate.opsForValue().set(key, value, l, unit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存获取
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (null != key && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 查找所有符合给定模式 pattern 的 key
	 * 
	 * @param pattern
	 *            模式，如下面几个例子 KEYS * 匹配数据库中所有 key 。 KEYS h?llo 匹配 hello ， hallo 和
	 *            hxllo 等。 KEYS h*llo 匹配 hllo 和 heeeeello 等。 KEYS h[ae]llo 匹配
	 *            hello 和 hallo ，但不匹配 hillo 。
	 * @return 符合给定模式的 key 列表。
	 */
	public Set<String> keys(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		return keys;
	}

	/**
	 * 删除所有符合给定模式 pattern 的 key
	 * 
	 * @param pattern
	 */
	public void clearCache(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}

	public boolean exists(String key){
		return redisTemplate.hasKey(key);
	}

	public String hget(String h,Object o){
		return redisTemplate.opsForHash().get(h,o) == null ? null:redisTemplate.opsForHash().get(h,o).toString();
	}

	public void hset(String h,Object o,Object hv){
		 redisTemplate.opsForHash().put(h,o,hv);
	}

	public long increment(String redisKey,long time){
		return redisTemplate.opsForValue().increment(redisKey, time);
	}

	public void expire(String redisKey,long time,TimeUnit timeUnit){
		redisTemplate.expire(redisKey, 30,timeUnit);
	}
}

package com.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTools<T> {
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * String类的存值
     *
     * **/
    public void SetString(String Key,Object Value){
        redisTemplate.opsForValue().set(Key,Value);
    }
    /**
     * 修改数据
     *
     * **/
    public void ChangeString(String Key,T Value){
        redisTemplate.opsForValue().getAndSet(Key,Value);
    }

    public boolean isBlank(String Key){
      return redisTemplate.hasKey(Key);
    }

    /**
     * String取值
     *
     * **/
    public String GetString(Object Key){
        return (String) redisTemplate.opsForValue().get(Key);
    }

    /**
     * List向左边边插入
     *
     * **/
    public void SetLeftList(String Key,Object Value){
        redisTemplate.opsForList().leftPush(Key,Value);
    }

    /**
     * List向右边插入
     *
     * **/
    public void SetRightList(String Key,Object Value){
        redisTemplate.opsForList().rightPush(Key,Value);
    }

    /**
     * List向左边批量插入数据
     *
     * **/
    public Long SetLeftListMore(String Key, List<Object>Value){
        return redisTemplate.opsForList().leftPushAll(Key,Value);
    }
    /**
     * List向右批量插入数据
     *
     * **/
    public Long SetRightListMore(String Key, List<Object>Value){
        return redisTemplate.opsForList().rightPush(Key,Value);
    }


    /**
     * 通过返回所有List中值
     *
     * **/
    public List<Object> SetLeftListMore(String Key){
        return redisTemplate.opsForList().range
                (Key, 0,redisTemplate.opsForList().size("Key")-1);
    }

    /**
     * 写入redis缓存（不设置expire存活时间）
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value){
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            System.out.println("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(final String key, T value, Long expire){
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            System.out.println("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }


    /**
     * 读取redis缓存
     * @param key
     * @return
     */
    public Object get(final String key){
        Object result = null;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            System.out.println("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exists(final String key){
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            System.out.println("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     * @param key
     * @return
     */
    public boolean remove(final String key){
        boolean result = false;
        try {
            if(exists(key)){
                redisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            System.out.println("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     * @param keys
     * @return
     */
    public void remove(final String... keys){
        for(String key : keys){
            remove(key);
        }
    }

}

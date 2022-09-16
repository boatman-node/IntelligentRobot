package com;

import com.Service.RedisTools;
import com.Tools.HttpConfigure;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@SpringBootTest
class IntelligentRobotApplicationTests {
    @Autowired
    HttpConfigure configure;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpConfigure httpConfigure;
    @Autowired
    RedisTools redisTools;
    @Test
    void contextLoads1() {
       redisTemplate.opsForValue().increment("user",1);

    }
    @Test
    void contextLoads2() {
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
    @Test
    void contextLoads3() {

            for (int i= 0; i<5 ; i++) {
                String s = configure.GetReptileString("https://api.caonm.net/api/ciyuan/api.php?type=json", 20);
                System.out.println(s.substring(11, s.indexOf("\",")));
            }


    }

    @Test
    void contextLoads5() {
//        redisTemplate.opsForValue().increment("次数");
        System.out.println(redisTools.get("次2"));

    }
    @Test
    void contextLoads6() {
        redisTemplate.opsForValue().set("次2",1,7*60l);
    }



}

package com.Listener;

import com.Service.RedisTools;
import love.forte.simbot.annotation.Async;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

@OnGroup
@Component
public class GameListener implements com.ListenerInterface.GameListener {
    @Autowired
    RedisTools redisTools;
    @Autowired
    RedisTemplate redisTemplate;
    @Async
    @Filter(value = "猜数",matchType = MatchType.STARTS_WITH)
    @Override
    public void GuessTheNumber(GroupMsg privateMsg, MsgSender sender) {
        redisTools.set(privateMsg.getAccountInfo().getAccountCode(),(int) ((Math.random() * (1000 - 1)) + 1),7*60l);
        sender.SENDER.sendGroupMsg(privateMsg,"游戏开始"+"\n"+"初始化数据已经写入缓存！"+"\n" +"1000以内的随机数,以最少猜数的次数找出！");
    }
    @Async
    @Filter(value = "数字{{Number}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void Guess(GroupMsg privateMsg, MsgSender sender, @FilterValue("Number") int Number) {
        String NumberCode=privateMsg.getAccountInfo().getAccountCode();
        if(redisTools.isBlank(NumberCode)){
             if (Number == (int)redisTools.get(NumberCode)){
                sender.SENDER.sendGroupMsg(privateMsg,"输入正确!"+"\n正在清理缓存数据！");
                redisTools.remove(NumberCode);
             }else {
                sender.SENDER.sendGroupMsg(privateMsg,Number < (int)redisTools.get(NumberCode)?
                        "你输入的数字比实际数字小！"+"\n"+"尝试第"+redisTemplate.opsForValue().get(NumberCode+"次")+"次":
                        "你输入的数字比实际数字大！"+"\n"+"尝试第"+redisTemplate.opsForValue().get(NumberCode+"次")+"次");
             }
        }else {
              sender.SENDER.sendGroupMsg(privateMsg,"未初始你的数据！");
        }
    }

}

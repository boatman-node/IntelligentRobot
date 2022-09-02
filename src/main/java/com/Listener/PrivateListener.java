package com.Listener;

import com.Tools.CatCode;
import com.Tools.StringTools;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@OnPrivate
public class PrivateListener implements com.ListenerInterface.PrivateListener {
    @Autowired
    StringTools stringTools;
    @Autowired
    CatCode catCode;
    /*---------------------------------------------------------------------------*/
    /**菜单！
     * */
    @Filter(value = "菜单", matchType = MatchType.STARTS_WITH)
    @Override
    public void menuListener(PrivateMsg privateMsg, Sender sender) {
      sender.sendPrivateMsg(privateMsg,catCode.ShareCat("你好", "你是谁", "www.baidu.com"));
    }
}

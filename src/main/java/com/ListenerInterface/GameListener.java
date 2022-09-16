package com.ListenerInterface;

import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;

public interface GameListener {
    void  GuessTheNumber(GroupMsg privateMsg, MsgSender sender);
    void  Guess(GroupMsg privateMsg, MsgSender sender, int Number);
}

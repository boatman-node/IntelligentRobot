package com.ListenerInterface;

import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;

public interface PrivateListener {
    void menuListener(PrivateMsg privateMsg, Sender sender);
}

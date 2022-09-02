package com.ListenerInterface;

import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;


public interface GroupListener {
    void SearchChoose(GroupMsg groupMsg,MsgSender msgSender,String search);
    void menuListener(GroupMsg privateMsg, MsgSender sender,String Number);
    void beautifulLegs(GroupMsg privateMsg, MsgSender sender);
    void SixtySeconds(GroupMsg privateMsg, MsgSender sender);
    void networkSnapshot(GroupMsg privateMsg, MsgSender sender, String Url);
    void NetEaseCloud(GroupMsg privateMsg, MsgSender sender, String Url);
    void codeGeneration(GroupMsg groupMsg,MsgSender sender,String Txt);
    void newCrown(GroupMsg groupMsg,MsgSender sender,String City);
    void YouthStudy(GroupMsg privateMsg, MsgSender sender);
    void Translate(GroupMsg groupMsg,MsgSender sender,String Text);
    void weather(GroupMsg groupMsg,MsgSender sender,String City);
    void  beautyVideo(GroupMsg privateMsg, MsgSender sender);
    void  csDnApi(GroupMsg privateMsg, MsgSender sender,String Msg);
    void  Movie(GroupMsg privateMsg, MsgSender sender,String Msg);
    void  dogLicking(GroupMsg privateMsg, MsgSender sender);

}

package com.Listener;

import com.Entity.AllInterfaces;
import com.Service.RedisTools;
import com.Tools.CatCode;
import com.Tools.HttpConfigure;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import love.forte.simbot.annotation.Async;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;


@OnGroup
@Component
public class GroupListener implements com.ListenerInterface.GroupListener {
    @Autowired
    AllInterfaces allInterfaces;
    @Autowired
    HttpConfigure configure;
    @Autowired
    CatCode catCode;
    @Autowired
    RedisTools redisTools;
    @Async
    /**---------------------------------------------------------------------------------------*/
    @Filter(value = "-{{search}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void SearchChoose(GroupMsg groupMsg, MsgSender msgSender,@FilterValue("search") String search) {
         String Url=""; JSONObject jsonObject ;
         switch (redisTools.GetString("Type")){
             case "歌曲":
                 try {
                     jsonObject= configure.run(redisTools.get("Url") + "&n=" + search).getJSONObject("data");
                     msgSender.SENDER.sendGroupMsg(groupMsg,jsonObject.getString("song")+"\n"+jsonObject.getString("singer")+"\n"+jsonObject.getString("url")+catCode.CatPicture(jsonObject.getString("picture")));
                     msgSender.SENDER.sendGroupMsg(groupMsg,catCode.voiceCat(jsonObject.getString("music")));
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 break;
             case "csDn":
                 try {
                     String string = configure.run(redisTools.GetString("Url") + "&n=" + search).getString("url_location");
                     System.out.println(string);
                     msgSender.SENDER.sendGroupMsg(groupMsg,"网站：   "+string);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 break;
             case "电影":
                 try {
                     JSONObject jsonObject1 = configure.run(redisTools.GetString("Url") + "&b=" + search.substring(0, 1) + "&n=" + search.substring(1, search.length())).getJSONObject("data");
                     msgSender.SENDER.sendGroupMsg(groupMsg,"电影    "+jsonObject1.getString("title")+"\n"
                                       +"评分    "+jsonObject1.getString("pingfen")+"\n"
                                       +"介绍    "+jsonObject1.getString("jieshao")+"\n"
                                       +"集数    "+jsonObject1.getString("jishu ")+"\n"
                                       +"资源   "+jsonObject1.getString("url")+"\n"
                             );
                     break;
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
         }
    }


    @Async
    @Filter(value = "美腿{{Number}}", matchType = MatchType.REGEX_MATCHES)
    @Filter(value = "古风{{Number}}", matchType = MatchType.REGEX_MATCHES)
    @Filter(value = "美女{{Number}}", matchType = MatchType.REGEX_MATCHES)
    @Filter(value = "cosplay{{Number}}",matchType = MatchType.REGEX_MATCHES )
    @Filter(value = "二次元{{Number}}", matchType = MatchType.REGEX_MATCHES)
    @Override
    public void menuListener(GroupMsg privateMsg, MsgSender sender,@FilterValue("Number") String Number) {
        System.out.println(redisTools.isBlank("transfer"));
        if(!redisTools.isBlank("transfer")){
        String Url=null;
        switch (privateMsg.getText().substring(0,privateMsg.getMsg().length()-1)){
            case "美腿":
                Url=allInterfaces.getBeautifulLegs();break;
            case "美女":
                Url=allInterfaces.getBeauty();break;
            case "cosplay":
                Url=allInterfaces.getCosplay();break;
            case "古风":
                Url=allInterfaces.getAntiquity();break;
            case "二次元":
                Url=allInterfaces.getTwoDimensional();break;
        }
        System.out.println(Url);
        if(Url!=Number){int Number1 = Integer.parseInt(Number);
            if (Number1>0&Number1<=5){
                for (int i= 0; i<Number1; i++) {
                    System.out.println(Number1);
                    sender.SENDER.sendGroupMsg(privateMsg,catCode.CatPicture(Url));
                }
            }else {
                sender.SENDER.sendGroupMsg(privateMsg,"你个鸡巴少看几张！");
            }}
          redisTools.set("transfer","他妈的!", 1L);
        }else sender.SENDER.sendGroupMsg(privateMsg,redisTools.GetString("transfer"));
     }


    @Async
    @Filter(value = "涩图", matchType = MatchType.STARTS_WITH)
    @Override
    public void beautifulLegs(GroupMsg privateMsg, MsgSender sender) {
      sender.SENDER.sendGroupMsg(privateMsg,catCode.CatPicture(""));
    }
    @Async
    @Filter(value = "60", matchType = MatchType.STARTS_WITH)
    @Override
    public void SixtySeconds(GroupMsg privateMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(privateMsg,catCode.CatPicture(allInterfaces.getSixtySeconds()));
    }
    @Async
    @Filter(value = "截图{{Url}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void networkSnapshot(GroupMsg privateMsg, MsgSender sender,@FilterValue("Url")String Url) {
        sender.SENDER.sendGroupMsg(privateMsg,catCode.CatPicture(allInterfaces.getNetworkSnapshot()+Url));
    }
    @Async
    @Filter(value = "搜歌{{Song}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void NetEaseCloud(GroupMsg privateMsg, MsgSender sender, @FilterValue("Song") String Song) {
        String stringBuilder="";
        try {
            String Url=allInterfaces.getQqMusic()+Song;
            JSONArray jsonArray = configure.run(Url).getJSONArray("Msg");
            redisTools.SetString("Type","歌曲");
            redisTools.SetString("Url",Url);
            for (int u=0;u<jsonArray.size();u++){
                stringBuilder=stringBuilder+(u+1)+": "+jsonArray.get(u)+"\n";}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            sender.SENDER.sendGroupMsg(privateMsg,stringBuilder.toString());
        }
    }
    @Async
    @Filter(value = "生成{{Text}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void codeGeneration(GroupMsg groupMsg, MsgSender sender,@FilterValue("Text") String Txt) {
        sender.SENDER.sendGroupMsg(groupMsg,catCode.CatPicture(allInterfaces.getCodeGeneration()+Txt));

    }
    @Async
    @Filter(value = "{{City}}疫情",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void newCrown(GroupMsg groupMsg, MsgSender sender,@FilterValue("City") String City) {
        sender.SENDER.sendGroupMsg(groupMsg,configure.GetReptileString(allInterfaces.getNewCrown()+City,3).replace("目前","累计").replace("\uD83C\uDF3E","\n"+"\uD83C\uDF3E"));
    }
    @Async
    @Filter(value = "青年大学习", matchType = MatchType.STARTS_WITH)
    @Override
    public void YouthStudy(GroupMsg privateMsg, MsgSender sender) {
     sender.SENDER.sendGroupMsg(privateMsg,configure.GetReptileString(allInterfaces.getYouthStudy(),4));
    }
    @Async
    @Filter(value = "翻译{{Text}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void Translate(GroupMsg groupMsg, MsgSender sender,@FilterValue("Text") String Text) {
        JSONObject data = null;
        try {
            data = configure.run(allInterfaces.getTranslate() + Text).getJSONObject("data");
            sender.SENDER.sendGroupMsg(groupMsg,"翻译前:   "+data.get("text")+"\n"+"翻译后:   "+data.get("fanyi"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Async
    @Filter(value = "{{City}}天气",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void weather(GroupMsg groupMsg, MsgSender sender,@FilterValue("City") String City) {
        try {
            String weatherstatus = configure.run(allInterfaces.getWeather() + City).getString("Weatherstatus");
            sender.SENDER.sendGroupMsg(groupMsg,weatherstatus.substring(1,weatherstatus.length()-1).replace(",","\uD83C\uDF3E"+"\n").replace("\"",""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Async
    @Filter(value = "多实战",matchType = MatchType.STARTS_WITH)
    @Override
    public void beautyVideo(GroupMsg privateMsg, MsgSender sender) {
       sender.SENDER.sendGroupMsg(privateMsg,allInterfaces.getBeautyVideo());
    }
    @Async
    @Filter(value = "csdn{{Msg}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void csDnApi(GroupMsg privateMsg, MsgSender sender, @FilterValue("Msg") String Msg) {
        String Url= allInterfaces.getCsDnApi()+Msg;
        redisTools.SetString("Type","csDn");
        redisTools.SetString("Url",Url);
        sender.SENDER.sendGroupMsg(privateMsg,configure.GetReptileString(Url,3).replace(" ","\n"));
    }
    @Filter(value = "电影{{Msg}}",   matchType = MatchType.REGEX_MATCHES)
    @Override
    public void Movie(GroupMsg privateMsg, MsgSender sender, @FilterValue("Msg") String Msg) {
        JSONObject run = null;
        String Url =allInterfaces.getMovie()+Msg;
        try {
            run = configure.run(Url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            String Str="";
            System.out.println(run.getString("msg")+"火影忍者" );
            if(run.getString("msg").length()<=4){
                redisTools.SetString("Type","电影");
                redisTools.SetString("Url",Url);
                JSONArray data = run.getJSONArray("data");
                for(int L=0;L<data.size();L++){
                    Str=Str+(L+1)+":   "+data.get(L)+"\n";}
                sender.SENDER.sendGroupMsg(privateMsg,Str);
            }else {
                sender.SENDER.sendGroupMsg(privateMsg,run.getString("msg"));
            }
        }


    }
    @Async
    @Filter(value = "日记",matchType = MatchType.STARTS_WITH)
    @Override
    public void dogLicking(GroupMsg privateMsg, MsgSender sender) {
     sender.SENDER.sendGroupMsg(privateMsg,configure.GetReptileString(allInterfaces.getDogLicking(),3));
    }


}

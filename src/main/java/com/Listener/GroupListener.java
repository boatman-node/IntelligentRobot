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
import love.forte.simbot.filter.AtDetection;
import love.forte.simbot.filter.MatchType;
import org.apache.logging.log4j.util.Strings;
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
         switch ((String) redisTools.GetString("Type")){
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
    @Async
    @Filter(value = "help",matchType = MatchType.STARTS_WITH)
    @Override
    public void Help(GroupMsg privateMsg, MsgSender sender) {
        String Help="----------          菜单          ----------"+"\n"+" 1        图片          "+"\n"+"2        搜歌 "+"\n"+
                "3        日记 "+"\n"+"4        (追加城市)疫情"+"\n"+
                "5        (追加城市)天气" +"\n"+
                "6        截图(后面追加网站)" +"\n"+
                "7        电影(功能暂时不稳定) " +"\n"+
                "8        60(新闻) " +"\n"+
                "9        csdn(后面追加内容) " +"\n"+
                "10       多实战 " +"\n"+
                "11       翻译(翻译内容) " +"\n"+
                "12       生成(后面追加内容，将文本转换二维码) 详细：-为选择，例如第一首歌曲就是 -1！ 电影 选择方式为 -11  前面第一个1为列表 中第一个而第二个1是第一集     图片类型则有古风 美女 美腿 二次元 cosplay 后面追加数字则是发送几张，切记最大一次性最多5张";
        sender.SENDER.sendGroupMsg(privateMsg,Help);
    }

    @Async
    @Filter(value = "拍拍",matchType = MatchType.STARTS_WITH)
    @Filter(value = "高质量",matchType = MatchType.STARTS_WITH)
    @Filter(value = "膜拜",matchType = MatchType.STARTS_WITH)
    @Filter(value = "撕",matchType = MatchType.STARTS_WITH)
    @Filter(value = "遗照",matchType = MatchType.STARTS_WITH)
    @Filter(value = "警官证",matchType = MatchType.STARTS_WITH)
    @Filter(value = "光棍证",matchType = MatchType.STARTS_WITH)
    @Filter(value = "老司机",matchType = MatchType.STARTS_WITH)
    @Filter(value = "屌丝证",matchType = MatchType.STARTS_WITH)
    @Filter(value = "帅哥证",matchType = MatchType.STARTS_WITH)
    @Filter(value = "高富帅证",matchType = MatchType.STARTS_WITH)
    @Filter(value = "全群最帅",matchType = MatchType.STARTS_WITH)
    @Override
    public void Emoji(GroupMsg privateMsg, MsgSender sender) {
        String Url="";
      switch (privateMsg.getMsg().toString()){
          case "拍拍":Url=allInterfaces.getTouch();break;
          case "高质量":Url=allInterfaces.getHighQuality();break;
          case "膜拜":Url=allInterfaces.getWorship();break;
          case "撕":Url= allInterfaces.getRip();break;
          case "遗照":Url=allInterfaces.getPosthumousPhoto();break;
          case "光棍证":Url=allInterfaces.getBachelor();break;
          case "老司机":Url=allInterfaces.getDriver();break;
          case "屌丝证":Url=allInterfaces.getDiaoSi();break;
          case "高富帅证":Url=allInterfaces.getHandsomeGuy();break;
          case "帅哥":Url=allInterfaces.getHandsome();break;
          case "全群最帅":Url=allInterfaces.getMostHandsome();break;
      }
        sender.SENDER.sendGroupMsg(privateMsg,catCode.CatPicture(Url+privateMsg.getAccountInfo().getAccountCode()));
    }
    @OnGroup()
    @Async
    @Override
    public void chatWith(GroupMsg privateMsg, MsgSender sender,AtDetection atDetection) {
        if(atDetection.atBot()){
        try {
            System.out.println(privateMsg.getText());
            JSONObject run = configure.run(allInterfaces.getWoodenFish() + privateMsg.getText());
            String replace = run.get("text").toString().replace("小源", "小木鱼").replace("男", "女");
            sender.SENDER.sendGroupMsg(privateMsg,replace);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}
    }
    @Filter(value = "绿茶",matchType = MatchType.STARTS_WITH)
    @Override
    public void greenTea(GroupMsg privateMsg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(privateMsg,catCode.voiceCat(allInterfaces.getGreenTea()));
    }
    @Filter(value = "表情包菜单",matchType = MatchType.STARTS_WITH)
    @Async
    @Override
    public void CertificateEmoji(GroupMsg groupMsg, MsgSender sender) {
        String CertificateEmoji="----------          表情包菜单           ---------\n"+
                "1. 拍拍\n"+"2. 高质量\n"+"3. 膜拜\n"+"4. 警官证\n"+"5. 光棍证\n"+"6. 老司机证\n"
                +"7. 屌丝证\n"+"8. 帅哥证\n"+"9. 高富帅证\n"+"10. 全群最帅\n";
        sender.SENDER.sendGroupMsg(groupMsg,CertificateEmoji);
    }


}

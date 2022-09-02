package com.Tools;

import love.forte.catcode.CatCodeUtil;
import love.forte.catcode.CodeBuilder;
import org.springframework.stereotype.Component;

@Component
public class CatCode {
    /**图片catCode*/
    public String CatPicture(String Url){
        final CodeBuilder<String> stringBuilder = CatCodeUtil.getInstance().getStringCodeBuilder("image",true);
        String build = stringBuilder
                .key("url").value(Url)
                .build();
        return build;
    }
    /**分享catCode*/
    public String ShareCat(String Title,String Content,String Url){
        final CodeBuilder<String> stringBuilder = CatCodeUtil.getInstance().getStringCodeBuilder("share",true);
        String build = stringBuilder
                .key("title").value(Title)
                .key("content").value(Content)
                .key("url").value(Url)
                .build();
        return build;
    }


    /**分享catCode*/
    public String voiceCat(String Url){
        final CodeBuilder<String> stringBuilder = CatCodeUtil.getInstance().getStringCodeBuilder("voice",true);
        String build = stringBuilder
                .key("url").value(Url)
                .build();
        return build;
    }
}

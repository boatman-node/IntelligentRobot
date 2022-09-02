package com.Entity;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Service
@Getter
public class AllInterfaces {
    /**疫情查询*/
    private String epidemicInquiry="https://xiaobai.klizi.cn/API/other/yiqing.php?city=";
    /**全球ip查询*/
    private String ipSelect="https://xiaobai.klizi.cn/API/other/IP.php?IP=";
    /**60秒懂得世界*/
    private String sixtySeconds="https://xiaobai.klizi.cn/API/other/60s.php";
    /**快手查询---n*/
    private String quickQuery="https://xiaobai.klizi.cn/API/video/ks_Search.php?data=&msg=";
    /**bliBli查询---n*/
    private String bliBliQuery="https://xiaobai.klizi.cn/API/dxgn/bilibili.php?data=&msg=";
    /**美腿*/
    private String beautifulLegs="https://ovooa.com/API/meizi/api.php?type=image";
    /**美女*/
    private String Beauty="http://api.btstu.cn/sjbz/";
    /**古风*/
    private String antiquity="https://cdn.seovx.com/ha/?mom=302";
    /**Cosplay*/
    private String Cosplay="https://api.r10086.com/img-api.php?type=日本COS中国COS";
    /**二次元*/
    private String twoDimensional="https://cdn.seovx.com/d/?mom=302";
    /**涩图*/
    private String colorMap="https://api.guyunge.top/API/setu.php?type=json";
    /**网络快照*/
    private String networkSnapshot="http://ovooa.com/API/Website_snapshot/?url=";
    /**QQ音乐*/
    private String qqMusic="http://ovooa.com/API/QQ_Music/?Skey=&uin=&msg=";
    /**语音*/
    private String voice="https://ovooa.com/API/yuyin/api.php?msg=";
    /**二维码生成*/
    private String codeGeneration="https://api.xiao-xin.top/API/QRcode.php?msg=";
    /**新冠*/
    private String newCrown="http://api.sangbo520.cn/API/yqcx/yq.php?msg=";
    /**青年大学习*/
    private String YouthStudy="https://xiaobai.klizi.cn/API/other/youth.php";
    /**万能翻译*/
    private String  Translate = "https://api.vvhan.com/api/fy?text=";
    /**GitHub*/
    private String  GitHub = "http://bg.suol.cc/API/github/?msg=";
    /**天气预报*/
    private String  weather = "https://api.linhun.vip/api/theweather?city=";
    /**少看多实战*/
    private String  beautyVideo = "http://api.sangbo520.cn/API/mn/mn.php";
    /**Csdn*/
    private String  CsDnApi = "https://xiaobai.klizi.cn/API/other/csdn.php?msg=";
    /**电影*/
    private String  Movie = "http://api.xn--7gqa009h.top/api/ysss?msg=";
    /**舔狗日记*/
    private String  dogLicking = "https://ovooa.com/API/tgrj/api.php";
}

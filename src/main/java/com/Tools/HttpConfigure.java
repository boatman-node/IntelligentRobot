package com.Tools;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
@Component
public class HttpConfigure {
    OkHttpClient client;
    public JSONObject run(String url) throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(new URL(url))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return (JSONObject) JSONObject.parse(response.body().string());
        }

    }

    public  String GetReptileString(String Url,int TimeOut){
        try {
            Document parse = Jsoup.parse(new URL(Url), TimeOut*1000);
            return parse.body().html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}

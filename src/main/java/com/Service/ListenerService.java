package com.Service;

import com.Entity.AllInterfaces;
import com.Tools.HttpConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListenerService {
    @Autowired
    HttpConfigure httpConfigure;
    @Autowired
    AllInterfaces allInterfaces;
    public void GetNetEaseCloud(String Song){
    return;
    }
}

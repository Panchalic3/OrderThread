package com.example.demo.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class service {

    @Autowired
    Set<Notification> notificationSet;

    public void not(){
        if(notificationSet.contains("10")){
            System.out.println("has 10");
        }
        else {
            System.out.println("no 10 has re");
        }
    }
}
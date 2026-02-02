package com.example.demo.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SMS implements Notification{
    @Override
    public void send() {
        System.out.println("sms");
    }
}
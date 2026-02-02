package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component
public class Whatsaap implements Notification{
    @Override
    public void send() {
        System.out.println("whats upppp");
    }
}
package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component
public class Email implements Notification{
    @Override
    public void send() {
        System.out.println("email aa");
    }
}
package com.example.demo.util;

public class EmailUtil {
    public boolean validateEmails(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException();
        }

        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

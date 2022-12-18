package com.example.MG_RestaurantManager20.mail.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String to, String text);
}

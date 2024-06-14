package com.mika.mikabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOTPEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nhatkhangnguyen1111@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}

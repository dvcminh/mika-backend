package com.mika.mikabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    public String sendEmailToAllUsers(String subject, String text) {
        userService.getAllUsers().forEach(user -> {
            sendEmail(user.getEmail(), subject, text);
        });
        return "Email sent to all users";
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Async
    public void sendCustomerOrderDetailLinkEmail(String email, String subjectEmail, String order_id) {
        String text = "Your order has been created successfully, here are you links to track your order:" +
                "http://localhost:5174/order/" + order_id;

        sendEmail(email, subjectEmail, text);
    }

    @Async
    public void sendAdminOrderDetailLinkEmail(String email, String subjectEmail, String order_id) {
        String text = "Your order has been created successfully, here are you links to track your order:" +
                "http://localhost:5173/order/" + order_id;

        sendEmail(email, subjectEmail, text);
    }
}

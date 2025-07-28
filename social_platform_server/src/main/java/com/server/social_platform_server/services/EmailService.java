//package com.server.social_platform_server.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendVerificationEmail(String toEmail, String token) {
//        String subject = "Email Verification";
//        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;
//        String body = "Click the link to verify your account:\n" + verificationLink;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);
//    }
//}


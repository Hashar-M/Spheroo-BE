package com.qburst.spherooadmin.email;

import com.qburst.spherooadmin.constants.EmailConstants;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    private JavaMailSender javaMailSender;

    @Override
    public void sendForgotPasswordMail(String to, String text, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(EmailConstants.SUBJECT);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}

package com.qburst.spherooadmin.email;

import com.qburst.spherooadmin.constants.EmailConstants;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Hameel
 * The implementation of the Email Service interface
 */
@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    /**
     * All email related tasks are taken care of by the Java Mail Sender
     */
    private JavaMailSender javaMailSender;

    /**
     * Sends the forgot password email to the user
     * @param to The email of the user to send it to
     * @param text The body of the email
     * @param token The password reset token
     */
    @Override
    public void sendForgotPasswordMail(String to, String text, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(EmailConstants.SUBJECT);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}

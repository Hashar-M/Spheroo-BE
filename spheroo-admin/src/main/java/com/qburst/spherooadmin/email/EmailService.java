package com.qburst.spherooadmin.email;

public interface EmailService {
    void sendForgotPasswordMail(String to, String text, String token);
}

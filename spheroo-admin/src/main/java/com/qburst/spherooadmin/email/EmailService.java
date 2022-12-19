package com.qburst.spherooadmin.email;

/**
 * @author Hameel
 * The email service is responsible for all e-mail related tasks.
 */
public interface EmailService {
    /**
     * Sends the forgot password email to a user
     * @param to The email of the user to send it to
     * @param text The body of the email
     * @param token The password reset token
     */
    void sendForgotPasswordMail(String to, String text, String token);
}

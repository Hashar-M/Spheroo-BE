package com.qburst.spherooadmin.exception;

/**
 * @author Hameel
 * this exception is thrown when a password reset token is expired.
 */
public class ResetTokenExpiredException extends RuntimeException{
    public ResetTokenExpiredException(String message) {
        super(message);
    }
}


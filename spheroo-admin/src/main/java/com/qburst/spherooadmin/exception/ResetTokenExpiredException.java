package com.qburst.spherooadmin.exception;

/**
 * this exception is thrown when a password reset token is expired.
 */
public class ResetTokenExpiredException extends RuntimeException{
    public ResetTokenExpiredException(String message) {
        super(message);
    }
}


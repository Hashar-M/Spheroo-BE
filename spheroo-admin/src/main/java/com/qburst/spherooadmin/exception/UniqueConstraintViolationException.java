package com.qburst.spherooadmin.exception;

/**
 * this exception handles exceptions which are occurring by giving wrong data to any Operations/Actions
 */
public class UniqueConstraintViolationException extends RuntimeException{
    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}

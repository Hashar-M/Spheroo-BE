package com.qburst.spherooadmin.exception;

/**
 * Exception is thrown while a {@link com.qburst.spherooadmin.service.Service} entity have name that is already in use.
 */
public class ServiceNameConstraintException extends RuntimeException{
    public ServiceNameConstraintException(String message) {
        super(message);
    }
}

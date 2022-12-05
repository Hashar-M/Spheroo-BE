package com.qburst.spherooadmin.exception;

/**
 * this exception handles exceptions which are occurring by giving wrong data to any Operations/Actions
 */
public class WrongDataForActionException extends RuntimeException{
    public WrongDataForActionException(String message) {
        super(message);
    }
}

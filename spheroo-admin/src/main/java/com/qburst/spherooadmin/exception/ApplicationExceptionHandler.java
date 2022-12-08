package com.qburst.spherooadmin.exception;

import com.qburst.spherooadmin.constants.ResponseConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Global exception handler
 * Handles most errors that come up in the Controller when it calls a service and gives out a standard response.
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This  exception is supposed to be called when trying to access an Entity that
     * does not exist in the database.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * This exception is supposed to be called when trying to delete an object from the database
     * that does not exist.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ResponseConstants.DATA_ACCESS_EXCEPTION_RESPONSE), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * This exception is supposed to be called when dealing with exceptions related to the constraints,
     * type and values for a field in a table.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ResponseConstants.DATA_INTEGRITY_EXCEPTION_RESPONSE, ex.getMostSpecificCause().toString()), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * This is the exception that occurs when a validation check fails.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    /**
     * This exception is called when passing an Illegal argument to a method.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ResponseConstants.ILLEGAL_ARGUMENT_EXCEPTION_RESPONSE), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WrongDataForActionException.class)
    public ResponseEntity<Object> handleWrongDataForActionException(WrongDataForActionException ex){
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> multipartExceptionHandler(MultipartException ex){
        ErrorResponse error=new ErrorResponse(Arrays.asList(ex.getMessage()),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity handleFileNotFoundException(FileNotFoundException ex){
        ErrorResponse error = new ErrorResponse(Arrays.asList(ResponseConstants.FILE_NOT_NOT_FOUND_EXCEPTION_RESPONSE),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}

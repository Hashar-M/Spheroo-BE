package com.qburst.spherooadmin.exception;

/**
 * exception is thrown while a {@link com.qburst.spherooadmin.category.Category} does not exists.
 */
public class CategoryNotFoundException  extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

package com.qburst.spherooadmin.constants;

public class ResponseConstants {
    public static final String DATA_ACCESS_EXCEPTION_RESPONSE = "Cannot delete non-existing resource";
    public static final String DATA_INTEGRITY_EXCEPTION_RESPONSE = "Data Integrity Violation: we cannot process your request.";
    public static final String JWT_VALIDITY_EXCEPTION_RESPONSE = "JWT token is not valid.";
    public static final String FILTER_RUNTIME_EXCEPTION_RESPONSE = "BAD REQUEST";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_RESPONSE = "passed an illegal or inappropriate argument.";
    public static final String FILE_NOT_NOT_FOUND_EXCEPTION_RESPONSE = "File not found";
    /**{@value #SERVICE_NAME_ALREADY_IN_USE} value of SERVICE_NAME_ALREADY_IN_USE*/
    public static final String SERVICE_NAME_ALREADY_IN_USE="service name already in use.";
    public static final String PASSWORD_RESET_TOKEN_EXPIRED = "Your password reset request has expired.";
    public static final String PAGINATION_PAGE_NUMBER="page number should be non zero positive integers";
    public static final String PAGINATION_PAGE_SIZE="page size should be non zero positive integers";
    public static final String SUPPLIER_NAME_DUPLICATE_VALUE="supplier of given name already exists";
}

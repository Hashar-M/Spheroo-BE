package com.qburst.spherooadmin.constants;

public final class EmailConstants {
    public static final String SUBJECT = "Spheroo - Password Reset Request";
    public static final long EXPIRY_TIME = 2L;
    public static final String EMAIL_BODY = "Hello,\n\n Please click the following link to reset your password:\n";
    public static final String PASSWORD_RESET_PATH = "http://localhost:8080/reset-password/"; // This is a temporary path as FE path has not been implemented
}

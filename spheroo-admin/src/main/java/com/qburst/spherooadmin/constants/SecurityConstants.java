package com.qburst.spherooadmin.constants;

public class SecurityConstants {
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" token header
    public static final String BEARER = "Bearer ";
    public static final String SECRET_KEY = "!A%D*G-KaPdSgVkYp3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5";
    public static final int TOKEN_EXPIRATION = 7200000; //  2 hours in ms;
    public static final String REGISTER_PATH = "/registration";
    public static final String LOGIN_PATH = "/login";
    public static final String CONTENT_TYPE = "application/json";
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String ACTUATOR_PATH = "/actuator/**";
    public static final String API_DOCS_PATH = "/api-docs/**";
    public static final String API_DOCS_API_PATH = "/v3/api-docs/**";
    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    public static final String RESET_PASSWORD_PATH = "/reset-password/**";
}

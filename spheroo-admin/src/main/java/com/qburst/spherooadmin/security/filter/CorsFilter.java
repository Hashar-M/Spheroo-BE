package com.qburst.spherooadmin.security.filter;

import com.qburst.spherooadmin.constants.CorsConstants;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A CORS filter that adds the necessary headers for the front end to use.
 */
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(CorsConstants.CORS_ACCESS_ORIGIN_HEADER, CorsConstants.CORS_ORIGIN);
        response.setHeader(CorsConstants.CORS_ACCESS_METHOD_HEADER, CorsConstants.CORS_METHODS);
        response.setHeader(CorsConstants.CORS_ACCESS_MAX_AGE_HEADER, CorsConstants.CORS_MAX_AGE);
        response.setHeader(CorsConstants.CORS_ALLOWED_HEADERS, CorsConstants.CORS_ALLOWED_HEADERS);
        response.addHeader(CorsConstants.CORS_EXPOSED_HEADERS, CorsConstants.CORS_ACCESS_EXPOSE_HEADERS);
        if (CorsConstants.OPTIONS_METHOD.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

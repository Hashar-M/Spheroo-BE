package com.qburst.spherooadmin.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.spherooadmin.constants.SecurityConstants;
import com.qburst.spherooadmin.security.manager.CustomAuthenticationManager;
import com.qburst.spherooadmin.user.Users;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * When a user attempts authentication, the request goes through the authentication filter first
 * and provides a response based on whether the authentication was successful or not.
 */
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;

    /**
     * Attempts user authentication.
     * @param request The request from the client
     * @param response  The response to the client
     * @return Authentication object that contains the result of the authentication
     */
    @Override
    public Authentication attemptAuthentication(@NonNull HttpServletRequest request, HttpServletResponse response) {
        try {
            Users user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method is called on unsuccessful authentication
     * @param request The request from the client
     * @param response The response to the client
     * @param failed The AuthenticationException object
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    /**
     * Method is called on successful authentication.
     * Creates a JWT token and adds it to the response header.
     * It will also send back the JWT token in the response body.
     * @param request The request from the client
     * @param response The response to the client
     * @param chain The FilterChain object
     * @param authResult The result of the authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        response.setCharacterEncoding(SecurityConstants.CHARACTER_ENCODING);
        response.getWriter().write(
                "{\"" + SecurityConstants.AUTHORIZATION + "\":\"" + SecurityConstants.BEARER + token + "\"}"
        );

    }
}

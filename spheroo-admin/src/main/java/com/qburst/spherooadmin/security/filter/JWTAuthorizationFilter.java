package com.qburst.spherooadmin.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qburst.spherooadmin.constants.ResponseConstants;
import com.qburst.spherooadmin.constants.SecurityConstants;
import com.qburst.spherooadmin.user.UserServiceImpl;
import com.qburst.spherooadmin.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Hameel
 * The JWTAuthorizationFilter is responsible for verifying if the JWT token that is passed is valid or not
 */
// Authorization: Bearer JWT
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /**
     * The userService is needed because at a time only a single user can remain authenticated so we check the time
     * the JWT token was issued and compare it with the users last login time
     */
    private final UserServiceImpl userService;

    /**
     * Since the filters are running outside the spring application context we need to pass the userService object
     * from within the context to use it
     * @param userService the user service
     */
    @Autowired
    public JWTAuthorizationFilter(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * This method is responsible for verifying the validity of the JWT token and then passing the request back to the
     * other filters
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization"); // Bearer JWT

        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token);
        String user = decodedJWT
                .getSubject();
        Date issuedAtTime = decodedJWT
                .getIssuedAt();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Users> usersOptional = Optional.ofNullable(userService.getUserByEmailId(user));
        if (usersOptional.isPresent()) {
            if (usersOptional.get().getLastLogin().toInstant().truncatedTo(ChronoUnit.SECONDS).isAfter(issuedAtTime.toInstant())) {
                throw new JWTVerificationException(ResponseConstants.JWT_SESSION_EXCEPTION_RESPONSE);
            }
        }
        filterChain.doFilter(request, response);
    }
}


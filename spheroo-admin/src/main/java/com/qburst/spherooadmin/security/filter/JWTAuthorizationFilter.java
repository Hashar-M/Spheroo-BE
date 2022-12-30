package com.qburst.spherooadmin.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qburst.spherooadmin.constants.SecurityConstants;
import com.qburst.spherooadmin.user.Users;
import com.qburst.spherooadmin.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Verifies the JWT token
 */
// Authorization: Bearer JWT

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UsersRepository usersRepository;

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
        Optional<Users> usersOptional = Optional.ofNullable(usersRepository.findByEmailId(user));
//        if (usersOptional.isPresent()) {
//            if (usersOptional.get().getLastLogin().after(issuedAtTime)) {
//                throw new JWTVerificationException("Invalid Token!");
//            }
//        }
        filterChain.doFilter(request, response);
    }
}


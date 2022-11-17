package com.qburst.spherooadmin.security.manager;
import com.qburst.spherooadmin.user.UserService;
import com.qburst.spherooadmin.user.Users;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

/**
 * Custom authentication manager that authenticates the user.
 */
@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Authenticates the user.
     * @param authentication authentication object that contains the user details
     * @return Returns a username password authentication token.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Users> user = Optional.ofNullable(userService.getUserByEmailId(authentication.getName()));
        if (user.isPresent()) {
            if (authentication.getCredentials() != null && !bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword())) {
                throw new BadCredentialsException("Invalid Username or Password!");
            }
        } else {
            throw new BadCredentialsException("Invalid Username or Password!");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials());
    }
}

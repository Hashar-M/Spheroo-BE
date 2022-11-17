package com.qburst.spherooadmin.security;

import com.qburst.spherooadmin.constants.SecurityConstants;
import com.qburst.spherooadmin.security.filter.AuthenticationFilter;
<<<<<<< HEAD
import com.qburst.spherooadmin.security.filter.CorsFilter;
import com.qburst.spherooadmin.security.filter.ExceptionHandlerFilter;
=======
>>>>>>> 79e4184 (completed supplier model and implimented api for CRUD operations including pagination)
import com.qburst.spherooadmin.security.filter.JWTAuthorizationFilter;
import com.qburst.spherooadmin.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
<<<<<<< HEAD
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
=======
>>>>>>> 79e4184 (completed supplier model and implimented api for CRUD operations including pagination)

/**
 * Security configuration class that is used to configure spring security.
 */
@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private CustomAuthenticationManager customAuthenticationManager;

    /**
     * Each request is passed through the SecurityFilterChain and is processed to authenticate
     * and authorize each and every request.
     * @inheritDoc
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter= new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
<<<<<<< HEAD
                .cors().and()
=======
>>>>>>> 79e4184 (completed supplier model and implimented api for CRUD operations including pagination)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
<<<<<<< HEAD
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
=======
>>>>>>> 79e4184 (completed supplier model and implimented api for CRUD operations including pagination)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);
        return http.build();
    }
}

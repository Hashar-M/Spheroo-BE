package com.qburst.spherooadmin.security;

import com.qburst.spherooadmin.constants.SecurityConstants;
import com.qburst.spherooadmin.security.filter.AuthenticationFilter;
import com.qburst.spherooadmin.security.filter.CorsFilter;
import com.qburst.spherooadmin.security.filter.ExceptionHandlerFilter;
import com.qburst.spherooadmin.security.filter.JWTAuthorizationFilter;
import com.qburst.spherooadmin.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);
        return http.build();
    }
}

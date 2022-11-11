package com.qburst.spherooadmin.security;

import com.qburst.spherooadmin.security.filter.AuthenticationFilter;
import com.qburst.spherooadmin.security.filter.JWTAuthorizationFilter;
import com.qburst.spherooadmin.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter= new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        return http.build();
    }
}

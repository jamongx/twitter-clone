package com.jason.twitter.userservice.config;

import com.jason.twitter.userservice.security.JwtAuthenticationEntryPoint;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    // Handle unauthenticated access
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    // Validate JWT tokens in requests
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Set up security filter chain for HTTP requests
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/api/v1/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated(); // Require authentication for other requests
                }).httpBasic(Customizer.withDefaults()); // Use basic HTTP authentication

        // Use JwtAuthenticationEntryPoint to handle authentication failures
        httpSecurity.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        // UsernamePasswordAuthenticationFilter is one of the default authentication filters in Spring Security,
        // handling authentication based on username and password
        // By using addFilterBefore, the authenticationFilter is executed before this default filter
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Finish the Spring Security configuration,
        // and create a SecurityFilterChain object based on the configured security settings
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
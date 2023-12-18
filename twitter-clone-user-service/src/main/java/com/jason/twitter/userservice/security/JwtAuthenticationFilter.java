package com.jason.twitter.userservice.security;

import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.service.impl.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class integrates the information of users verified through JWT tokens into the Spring Security authentication mechanism.
 * As a result, the system recognizes the user as authenticated and permits access according to the user's authorities.
 * This process ensures that the user's identity is verified and their permissions are correctly assigned for the duration of their session.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Get JWT token from HTTP request
            String token = getTokenFromRequest(request);
            // Validate Token
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                // get username from token
                String username = jwtTokenProvider.getUsername(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                // Set additional details on the authentication token from the HTTP request.
                // This line extracts details such as IP address and session ID from the request,
                // using WebAuthenticationDetailsSource, and attaches them to the authentication token.
                // These details can be useful for auditing and tracking user activity within the application.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the SecurityContextHolder's context.
                // This line integrates the authenticated user's information into the Spring Security context,
                // making it available throughout the application. It signifies that the user has been successfully
                // authenticated, allowing the system to grant access and permissions based on the user's authorities.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error("Error occurred in JWT authentication process: {}", e.getMessage(), e);
        }
        // Indicates the end of processing in the current filter, and forwards the request and response to the next filter in the chain.
        // This signifies that it is now the turn of the next filter to process the request and response.
        // Once all filters in the chain have completed their processing, the request eventually reaches the servlet or controller responsible for handling it.
        // At this point, the actual business logic of the application is executed.
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.HTTP_AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.BEARER_TOKEN_PREFIX.length());
        }
        return null;
    }
}

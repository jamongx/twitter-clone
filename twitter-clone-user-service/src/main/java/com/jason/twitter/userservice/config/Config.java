package com.jason.twitter.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Value("${app.default-avatar-url}")
    private String defaultAvatarUrl;

    @Value("${app.users-avatar-dir}")
    private String usersAvatarDir;

    @Bean
    public String defaultAvatarUrl() {
        return defaultAvatarUrl;
    }

    @Bean
    public String usersAvatarDir() {
        return usersAvatarDir;
    }
}

package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.entity.Role;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;
import com.jason.twitter.userservice.exception.UserAPIException;
import com.jason.twitter.userservice.mapper.UserMapper;
import com.jason.twitter.userservice.repository.RoleRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.security.JwtTokenProvider;
import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final String defaultAvatarUrl;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider tokenProvider;

    @Transactional
    @Override
    public String register(RegisterDto registerDto) {
        validateUserExistence(registerDto.getUsername(), registerDto.getEmail());

        User user = createUser(registerDto);
        UserProfile userProfile = createUserProfile(registerDto);

        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        User createdUser = userRepository.save(user);
        return "User Registered Successfully!.";
    }

    @Transactional
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = getUserByUsernameOrEmail(loginDto.getUsernameOrEmail());
        return UserMapper.mapToJwtAuthResponse(user, token, SecurityConstants.BEARER_TOKEN_TYPE);
    }

    private void validateUserExistence(String username, String email) {
        if(userRepository.existsByUsername(username)) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if(userRepository.existsByEmail(email)) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
    }

    private User createUser(RegisterDto registerDto) {
        return User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
    }

    private UserProfile createUserProfile(RegisterDto registerDto) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole(SecurityConstants.ROLE_USER);
        roles.add(userRole);

        return UserProfile.builder()
                .avatarUrl(defaultAvatarUrl)
                .displayName(registerDto.getDisplayName())
                .bio(registerDto.getBio())
                .active(Boolean.TRUE)
                .birthDate(LocalDate.parse(registerDto.getBirthDate()))
                .roles(roles).build();
    }

    private User getUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
    }

}

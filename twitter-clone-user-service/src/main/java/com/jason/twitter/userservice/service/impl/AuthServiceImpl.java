package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.entity.Role;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.UserAPIException;
import com.jason.twitter.userservice.mapper.UserMapper;
import com.jason.twitter.userservice.repository.RoleRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.security.JwtTokenProvider;
import com.jason.twitter.userservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final String defaultAvatarUrl;

    private ModelMapper modelMapper;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {

        // check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = modelMapper.map(registerDto, User.class);
        //user.setAvatarUrl("/images/twitter-default-avatar.jpg");
        user.setAvatarUrl(defaultAvatarUrl);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setBirthDate(LocalDate.parse(registerDto.getBirthDate()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully!.";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
                loginDto.getUsernameOrEmail());
        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            jwtAuthResponse = UserMapper.mapToJwtAuthResponse(loggedInUser, token, "Bearer");
            jwtAuthResponse.setId(loggedInUser.getId());
            jwtAuthResponse.setUsername(loggedInUser.getUsername());
            jwtAuthResponse.setRoles(loggedInUser.getRoles());
        }

        return jwtAuthResponse;
    }
}

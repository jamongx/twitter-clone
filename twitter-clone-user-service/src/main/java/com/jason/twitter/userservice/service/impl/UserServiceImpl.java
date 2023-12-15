package com.jason.twitter.userservice.service.impl;


import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.entity.Role;
import com.jason.twitter.userservice.exception.UserAPIException;
import com.jason.twitter.userservice.mapper.UserMapper;
import com.jason.twitter.userservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        //TODO have to define type policy to create default password
        user.setPassword(passwordEncoder.encode("default password"));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole(SecurityConstants.ROLE_USER);
        roles.add(userRole);

        user.getUserProfile().setRoles(roles);

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new UserAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving user");
        }
        return UserMapper.mapToUserDto(savedUser);

    }


    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        if (!user.getUsername().equals(userDto.getUsername()) &&
                userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        user.setUsername(userDto.getUsername());

        if (!user.getEmail().equals(userDto.getEmail()) &&
                userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAPIException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " +id));
        userRepository.deleteById(id);
    }

}

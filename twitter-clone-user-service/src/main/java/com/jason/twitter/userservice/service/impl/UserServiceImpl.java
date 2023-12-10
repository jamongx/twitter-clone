package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.ProfileDto;
import lombok.AllArgsConstructor;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final String usersAvatarDir;
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        // convert UserDto into user Jpa entity
        User user = modelMapper.map(userDto, User.class);

        // User Jpa entity
        User savedUser = userRepository.save(user);

        // Convert saved User Jpa entity object into UserDto object
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

        user.setDisplayName(userDto.getDisplayName());
        user.setBio((userDto.getBio()));

        User updatedTodo = userRepository.save(user);

        return modelMapper.map(updatedTodo, UserDto.class);
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " +id));
        userRepository.deleteById(id);
    }

    @Override
    public UserDto activeUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        user.setActive(Boolean.TRUE);

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto deactiveUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        user.setActive(Boolean.FALSE);

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    // just uplaod an avatar image file not update user information
    @Override
    public AvatarDto addAvatar(Long id, MultipartFile file) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        String fileName = null;
        if (!file.isEmpty()) {
            try {
                Path uploadPath = Paths.get(usersAvatarDir);
                if (!uploadPath.toFile().exists()) {
                    uploadPath.toFile().mkdirs();
                }

                String originalFileName = file.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

                SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
                String dateFormatted = dateFormat.format(new Date());

                fileName = user.getUsername() + dateFormatted + fileExtension;

                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath);

            } catch (IOException e) {
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
            }
        }
        return new AvatarDto("/avatar/" + fileName);
    }

    @Override
    public ProfileDto updateProfile(ProfileDto profileDto) {
        User user = userRepository.findById(profileDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + profileDto.getId()));

        user.setAvatarUrl(profileDto.getAvatarUrl());
        user.setDisplayName(profileDto.getDisplayName());
        user.setBio(profileDto.getBio());
        user.setBio(profileDto.getBio());
        user.setActive(profileDto.isActive());
        user.setBirthDate(profileDto.getBirthDate());

        userRepository.save(user);

        return profileDto;
    }

}

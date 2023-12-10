package com.jason.twitter.userservice.service;


import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.ProfileDto;
import com.jason.twitter.userservice.dto.UserDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Long id);

    UserDto getUser(Long id);

    void deleteById(Long id);

    UserDto activeUser(Long id);

    UserDto deactiveUser(Long id);

    AvatarDto addAvatar(Long id, MultipartFile file);

    ProfileDto updateProfile(ProfileDto profileDto);

}

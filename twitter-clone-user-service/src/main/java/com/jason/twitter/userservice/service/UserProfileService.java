package com.jason.twitter.userservice.service;


import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.StatusDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {


    StatusDto updateStatus(Long id, boolean isActive);

    AvatarDto updateAvatar(Long id, MultipartFile file);

    UserProfileDto updateProfile(UserProfileDto userProfileDto);

}

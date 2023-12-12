package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.StatusDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.entity.UserProfile;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.repository.UserProfileRepository;
import com.jason.twitter.userservice.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final String usersAvatarDir;

    private UserProfileRepository userProfileRepository;

    private ModelMapper modelMapper;

    @Override
    public StatusDto updateStatus(Long id, boolean isActive) {
        int updatedRows = userProfileRepository.updateUserProfileActiveStatus(id, isActive);
        if (updatedRows == 0) {
            throw new ResourceNotFoundException("UserProfile not found with id : " + id);
        }
        return new StatusDto(isActive);
    }

    @Override
    @Transactional
    public AvatarDto updateAvatar(Long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file cannot be uploaded");
        }

        String fileName = storeFile(file, id);
        String avatarUrl = "/avatar/" + fileName;

        int updatedRows = userProfileRepository.updateUserProfileAvatarURL(id, avatarUrl);
        if (updatedRows == 0) {
            throw new ResourceNotFoundException("UserProfile not found with id : " + id);
        }
        return new AvatarDto(avatarUrl);
    }


    private String storeFile(MultipartFile file, Long userId) throws RuntimeException {
        try {
            Path uploadPath = prepareUploadPath();

            String fileExtension = extractFileExtension(file.getOriginalFilename());
            String fileName = formatFileName(userId, fileExtension);

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    private Path prepareUploadPath() throws IOException {
        Path uploadPath = Paths.get(usersAvatarDir);
        if (!uploadPath.toFile().exists()) {
            uploadPath.toFile().mkdirs();
        }
        return uploadPath;
    }

    private String extractFileExtension(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == 0) {
            throw new IllegalArgumentException("Invalid file extension for " + originalFileName);
        }
        return originalFileName.substring(dotIndex);
    }

    private String formatFileName(Long userId, String fileExtension) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyyMMdd_HHmmss");
        String dateFormatted = LocalDateTime.now().format(formatter);
        return userId + dateFormatted + fileExtension;
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(UserProfileDto userProfileDto) {
        UserProfile userProfile = userProfileRepository.findById(userProfileDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id : " + userProfileDto.getId()));

        updateEntityFromDto(userProfile, userProfileDto);

        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        return modelMapper.map(updatedUserProfile, UserProfileDto.class);
    }


    private void updateEntityFromDto(UserProfile userProfile, UserProfileDto userProfileDto) {
        userProfile.setAvatarUrl(userProfileDto.getAvatarUrl());
        userProfile.setDisplayName(userProfileDto.getDisplayName());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setActive(userProfileDto.isActive());
        userProfile.setBirthDate(userProfileDto.getBirthDate());
    }

}

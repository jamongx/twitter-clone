package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.entity.UserProfile;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.repository.UserProfileRepository;
import com.jason.twitter.userservice.repository.UserRepository;
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

    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;

    private ModelMapper modelMapper;

    private void setUserProfileActiveStatus(Long id, Boolean status) {
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id : " + id));
        userProfile.setActive(status);
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfileDto activeUser(Long id) {
        setUserProfileActiveStatus(id, Boolean.TRUE);
        UserProfile updatedUserProfile = userProfileRepository.findById(id).get();
        return modelMapper.map(updatedUserProfile, UserProfileDto.class);
    }

    @Override
    public UserProfileDto deactiveUser(Long id) {
        setUserProfileActiveStatus(id, Boolean.FALSE);
        UserProfile updatedUserProfile = userProfileRepository.findById(id).get();
        return modelMapper.map(updatedUserProfile, UserProfileDto.class);
    }

    // just uplaod an avatar image file not update user information
    @Override
    public AvatarDto updateAvatar(Long id, MultipartFile file) {
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id : " + id));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file cannot be uploaded");
        }

        String fileName = storeFile(file, userProfile.getUser().getId());
        return new AvatarDto("/avatar/" + fileName);
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
        // 여기에 필요한 필드 업데이트 로직 추가
        userProfile.setAvatarUrl(userProfileDto.getAvatarUrl());
        userProfile.setDisplayName(userProfileDto.getDisplayName());
        userProfile.setBio(userProfileDto.getBio());
        userProfile.setActive(userProfileDto.isActive());
        userProfile.setBirthDate(userProfileDto.getBirthDate());
        // 필요한 유효성 검증 로직 추가
    }

}

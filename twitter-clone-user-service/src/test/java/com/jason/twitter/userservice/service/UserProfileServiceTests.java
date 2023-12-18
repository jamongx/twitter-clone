package com.jason.twitter.userservice.service;

import com.jason.twitter.userservice.dto.*;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.repository.UserProfileRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.impl.UserProfileServiceImpl;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTests {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private User user;

    @BeforeEach
    public void setup() {
        user = TestData.createTestUsers(1).get(0);
        // Use ReflectionTestUtils to set the private field 'usersAvatarDir' in userProfileService.
        // This sets the default directory for user avatars to '/images/twitter-default-avatar.jpg'.
        ReflectionTestUtils.setField(userProfileService, "usersAvatarDir", "/images/twitter-default-avatar.jpg");
    }

    @DisplayName("JUnit test for updateStatus method")
    @Test
    public void givenUserIdAndStatus_whenUpdateStatus_thenReturnStatusDto() {
        // given
        UserProfile userProfile = user.getUserProfile();
        boolean newStatus = true;
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userProfileRepository.updateUserProfileActiveStatus(userProfile.getId(), newStatus)).willReturn(1);

        // when
        StatusDto statusDto = userProfileService.updateStatus(user.getId(), newStatus);

        // then
        assertThat(statusDto).isNotNull();
        assertThat(statusDto.isActive()).isEqualTo(newStatus);
    }

    @DisplayName("JUnit test for updateAvatar method")
    @Test
    public void givenUserIdAndAvatarFile_whenUpdateAvatar_thenReturnAvatarDto() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        given(file.getOriginalFilename()).willReturn("twitter-default-avatar.jpg");
        given(file.isEmpty()).willReturn(false);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userProfileRepository.updateUserProfileAvatarURL(
                anyLong(),
                anyString())).willReturn(1);

        // when
        AvatarDto avatarDto = userProfileService.updateAvatar(user.getId(), file);

        // then
        assertThat(avatarDto).isNotNull();
        assertThat(avatarDto.getAvatarUrl()).isNotNull();
    }

    @DisplayName("JUnit test for updateProfile method")
    @Test
    public void givenUserProfileDto_whenUpdateProfile_thenReturnUpdatedUserProfileDto() {
        // given
        UserProfile userProfile = user.getUserProfile();
        UserProfileDto userProfileDto = Mapper.mapToUserProfileDto(userProfile);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userProfileRepository.findById(userProfileDto.getId())).willReturn(Optional.of(userProfile));
        given(userProfileRepository.save(any(UserProfile.class))).willReturn(userProfile);

        // when
        UserProfileDto updatedUserProfileDto = userProfileService.updateProfile(userProfileDto);

        // then
        assertThat(updatedUserProfileDto).isNotNull();
    }
}

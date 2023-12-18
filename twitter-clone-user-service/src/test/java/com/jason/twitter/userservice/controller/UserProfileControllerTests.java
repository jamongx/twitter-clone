package com.jason.twitter.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.StatusDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import com.jason.twitter.userservice.service.UserProfileService;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserProfileController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> users;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(10);
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserIdAndStatus_whenUpdateUserStatus_thenReturnUpdatedStatus() throws Exception {
        // given
        User user = users.get(0);
        Long userId = user.getId();
        boolean isActive = !user.getUserProfile().isActive();
        StatusDto statusDto = new StatusDto(user.getUserProfile().isActive());
        given(userProfileService.updateStatus(userId, isActive)).willReturn(statusDto);

        // when
        ResultActions response = mockMvc.perform(patch("/api/v1/user_profiles/{id}/status", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(isActive)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.active", is(statusDto.isActive())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserIdAndAvatarFile_whenUpdateAvatar_thenReturnUpdatedAvatar() throws Exception {
        // given
        User user = users.get(0);
        Long userProfileId = user.getUserProfile().getId();
        AvatarDto avatarDto = new AvatarDto("twitter-new-avatar.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "twitter-new-avatar.jpg", "image/jpeg", "avatar image content".getBytes());
        given(userProfileService.updateAvatar(userProfileId, file)).willReturn(avatarDto);

        // when
        ResultActions response = mockMvc.perform(multipart("/api/v1/user_profiles/{id}/avatar", userProfileId)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("PATCH"); // Set the request method to PATCH
                    return request;
                }));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.avatarUrl", is(avatarDto.getAvatarUrl())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserProfileDto_whenUpdateProfile_thenReturnUpdatedUserProfile() throws Exception {
        // given
        User user = users.get(1);
        Long userId = user.getId();
        UserProfileDto updatedUserProfileDto = Mapper.mapToUserProfileDto(user.getUserProfile());
        given(userProfileService.updateProfile(any(UserProfileDto.class))).willReturn(updatedUserProfileDto);

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/user_profiles/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserProfileDto)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(updatedUserProfileDto.getId().intValue())))
                .andExpect(jsonPath("$.avatarUrl", is(updatedUserProfileDto.getAvatarUrl())))
                .andExpect(jsonPath("$.displayName", is(updatedUserProfileDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(updatedUserProfileDto.getBio())))
                .andExpect(jsonPath("$.active", is(updatedUserProfileDto.isActive())))
                .andExpect(jsonPath("$.birthDate", is(updatedUserProfileDto.getBirthDate().toString())));
    }
}

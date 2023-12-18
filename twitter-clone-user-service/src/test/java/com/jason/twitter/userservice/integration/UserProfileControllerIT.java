package com.jason.twitter.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.StatusDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.repository.UserProfileRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@Sql("/data.sql")
public class UserProfileControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> users;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        users = TestData.createTestUsers(3);
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserIdAndStatus_whenUpdateUserStatus_thenReturnUpdatedStatus() throws Exception {
        // given
        User user = users.get(0);
        userRepository.save(user);

        Long userId = user.getId();
        boolean isActive = user.getUserProfile().isActive();
        StatusDto statusDto = new StatusDto(!isActive);

        // when
        ResultActions response = mockMvc.perform(patch("/api/v1/user_profiles/{id}/status", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(!isActive)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.active", is(statusDto.isActive())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserIdAndAvatarFile_whenUpdateAvatar_thenReturnUpdatedAvatar() throws Exception {
        // given
        User user = users.get(1);
        userRepository.save(user);

        Long userId = user.getId();
        MockMultipartFile file = new MockMultipartFile("file", "twitter-user-avatar.jpg", "image/jpeg", "avatar image content".getBytes());

        // when
        ResultActions response = mockMvc.perform(multipart("/api/v1/user_profiles/{id}/avatar", userId)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("PATCH"); // Set the request method to PATCH
                    return request;
                }));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                // "/avatar/1_20231215_221934.jpg"
                .andExpect(jsonPath("$.avatarUrl", matchesPattern("^/avatar/\\d+_\\d{8}_\\d{6}\\.jpg$")));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserProfileDto_whenUpdateProfile_thenReturnUpdatedUserProfile() throws Exception {
        // given
        User user = users.get(2);
        userRepository.save(user);

        Long userId = user.getId();
        UserProfileDto updatedUserProfileDto = Mapper.mapToUserProfileDto(users.get(1).getUserProfile());

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/user_profiles/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserProfileDto)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(userId.intValue())))
                .andExpect(jsonPath("$.avatarUrl", is(updatedUserProfileDto.getAvatarUrl())))
                .andExpect(jsonPath("$.displayName", is(updatedUserProfileDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(updatedUserProfileDto.getBio())))
                .andExpect(jsonPath("$.active", is(updatedUserProfileDto.isActive())))
                .andExpect(jsonPath("$.birthDate", is(updatedUserProfileDto.getBirthDate().toString())));
    }
}

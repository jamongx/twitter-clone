package com.jason.twitter.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import com.jason.twitter.userservice.service.UserService;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Part of the Jackson library,
    // used for converting JSON data to Java objects (deserialization)
    // and Java objects to JSON data (serialization).
    @Autowired
    private ObjectMapper objectMapper;

    private List<User> users;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(3);
    }

    // JUnit test for getUserById REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenGetUserById_thenReturnUserObject() throws Exception {
        // given - precondition or setup
        UserDto userDto = Mapper.mapToUserDto(users.get(0));
        Long userId = userDto.getId();
        given(userService.getUser(userId)).willReturn(userDto);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/users/{id}", userId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(userId.intValue())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.displayName", is(userDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(userDto.getBio())))
                .andExpect(jsonPath("$.avatarUrl", is(userDto.getAvatarUrl())))
                .andExpect(jsonPath("$.active", is(userDto.getActive())))
                .andExpect(jsonPath("$.birthDate", is(userDto.getBirthDate().toString())));
    }

    // JUnit test for getAllUsers REST API
    @Test
    @WithMockUser("Tester")
    public void whenGetAllUsers_thenReturnUsersList() throws Exception {
        List<UserDto> userDtos = Mapper.mapToUserDtos(users);
        given(userService.findAll()).willReturn(userDtos);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(userDtos.size())));
    }

    // JUnit test for addUser REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserObject_whenAddUser_thenReturnSavedUser() throws Exception {
        UserDto userDto = Mapper.mapToUserDto(users.get(0));
        given(userService.addUser(any(UserDto.class))).willReturn(userDto);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        // then - verify the output
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.displayName", is(userDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(userDto.getBio())))
                .andExpect(jsonPath("$.avatarUrl", is(userDto.getAvatarUrl())))
                .andExpect(jsonPath("$.active", is(userDto.getActive())))
                .andExpect(jsonPath("$.birthDate", is(userDto.getBirthDate().toString())));
    }

    // JUnit test for updateUser REST API
    @Test
    @WithMockUser("Tester")
    public void givenUpdatedUser_whenUpdateUser_thenReturnUpdatedUserObject() throws Exception {
        // given - precondition or setup
        UserDto userDto = Mapper.mapToUserDto(users.get(0));
        Long userId = userDto.getId();
        UserDto updatedUserDto = userDto;
        given(userService.updateUser(any(UserDto.class), eq(userId))).willReturn(updatedUserDto);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDto)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.displayName", is(userDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(userDto.getBio())))
                .andExpect(jsonPath("$.avatarUrl", is(userDto.getAvatarUrl())))
                .andExpect(jsonPath("$.active", is(userDto.getActive())))
                .andExpect(jsonPath("$.birthDate", is(userDto.getBirthDate().toString())));
    }

    // JUnit test for deleteUser REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {
        // given - precondition or setup
        Long userId = 1L;
        willDoNothing().given(userService).deleteById(userId);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/users/{id}", userId));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}

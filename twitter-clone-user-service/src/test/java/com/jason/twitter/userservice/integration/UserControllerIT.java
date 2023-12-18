package com.jason.twitter.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.mapper.Mapper;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@Sql("/data.sql")
public class UserControllerIT extends AbstractContainerBaseTest {

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
        users = TestData.createTestUsers(2);
    }

    // JUnit test for getUserById REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenGetUserById_thenReturnUserObject() throws Exception {
        // given - precondition or setup
        User savedUser = userRepository.save(users.get(0));
        Long userId = savedUser.getId();
        UserDto userDto = Mapper.mapToUserDto(savedUser);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/users/{id}", userId));

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

    // JUnit test for getAllUsers REST API
    @Test
    @WithMockUser("Tester")
    public void whenGetAllUsers_thenReturnUsersList() throws Exception {
        // given - precondition or setup
        List<User> savedUsers = userRepository.saveAll(users);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(savedUsers.size())));
    }

    // JUnit test for addUser REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserObject_whenAddUser_thenReturnSavedUser() throws Exception {
        // given - precondition or setup
        User user = users.get(0);
        UserDto userDto = Mapper.mapToUserDto(user);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        // then - verify the output
        response.andExpect(status().isCreated())
                .andDo(print())
                //.andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
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
        User user = users.get(0);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        UserDto updatedUserDto = Mapper.mapToUserDto(users.get(1));

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDto)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(updatedUserDto.getId().intValue())))
                .andExpect(jsonPath("$.username", is(updatedUserDto.getUsername())))
                .andExpect(jsonPath("$.email", is(updatedUserDto.getEmail())))
                .andExpect(jsonPath("$.displayName", is(updatedUserDto.getDisplayName())))
                .andExpect(jsonPath("$.bio", is(updatedUserDto.getBio())))
                .andExpect(jsonPath("$.avatarUrl", is(updatedUserDto.getAvatarUrl())))
                .andExpect(jsonPath("$.active", is(updatedUserDto.getActive())))
                .andExpect(jsonPath("$.birthDate", is(updatedUserDto.getBirthDate().toString())));
    }

    // JUnit test for deleteUser REST API
    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {
        // given - precondition or setup
        User user = users.get(0);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/users/{id}", userId));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}

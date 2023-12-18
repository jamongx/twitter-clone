package com.jason.twitter.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.constants.Constants;
import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@Sql("/data.sql")
public class AuthControllerIT extends AbstractContainerBaseTest {

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
        users = TestData.createTestUsers(10);
    }

    @Test
    @WithMockUser("Tester")
    public void givenRegisterDtoObject_whenRegisterUser_thenReturnSavedUser() throws Exception {
        // given
        RegisterDto registerDto = TestData.createRegisterDto(users.get(0));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)));

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(Constants.MSG_REGISTER_SUCC));
    }

    @Test
    @WithMockUser("Tester")
    public void loginTest() throws Exception {
        // given
        //userRepository.saveAll(users);
        givenRegisterDtoObject_whenRegisterUser_thenReturnSavedUser();

        User user = users.get(0);
        LoginDto loginDto = TestData.createLoginDto(user.getUsername(), user.getPassword());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }
}


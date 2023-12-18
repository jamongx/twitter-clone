package com.jason.twitter.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.constants.Constants;
import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import com.jason.twitter.userservice.service.AuthService;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setup() {
        user = TestData.createTestUsers(1).get(0);
    }

    // TODO no response body, Body is empty
    @Test
    @WithMockUser("Tester")
    public void givenRegisterDtoObject_whenRegisterUser_thenReturnSavedUser() throws Exception {
        // given
        String  resMsg = Constants.MSG_REGISTER_SUCC;
        RegisterDto registerDto = TestData.createRegisterDto(user);
        given(authService.register(registerDto)).willReturn(resMsg);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)));

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().string(resMsg));
    }

    @Test
    @WithMockUser("Tester")
    public void loginTest() throws Exception {
        // given
        LoginDto loginDto = TestData.createLoginDto(user.getUsername(), user.getPassword());
        JwtAuthResponse jwtAuthResponse = Mapper.mapToJwtAuthResponse(user, "TEST TOKEN", SecurityConstants.BEARER_TOKEN_TYPE);
        given(authService.login(any(LoginDto.class))).willReturn(jwtAuthResponse);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.accessToken", is(jwtAuthResponse.getAccessToken())));
    }
}


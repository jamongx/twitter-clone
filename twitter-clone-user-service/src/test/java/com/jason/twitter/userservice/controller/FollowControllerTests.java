package com.jason.twitter.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.security.JwtAuthenticationFilter;
import com.jason.twitter.userservice.service.FollowService;
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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FollowController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
public class FollowControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowService followService;

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
    public void givenUserId_whenGetFollowing_thenReturnFollowingList() throws Exception {
        Long userId = 100L;
        List<FollowerDto> followerDtos = TestData.createFollowerDtos(userId, users);
        given(followService.getFollowing(userId)).willReturn(followerDtos);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/follows/{id}/following", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", is(followerDtos.size())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenGetFollowers_thenReturnFollowersList() throws Exception {
        Long userId = 100L;
        List<FollowerDto> followerDtos = TestData.createFollowerDtos(userId, users);
        given(followService.getFollowers(userId)).willReturn(followerDtos);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/follows/{id}/followers", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", is(followerDtos.size())));
    }



    @Test
    @WithMockUser("Tester")
    public void givenFollowDto_whenAddFollow_thenReturnSavedFollow() throws Exception {
        Long userId = 100L;
        Long followingId = 200L;
        FollowDto followDto = new FollowDto(userId, followingId);
        given(followService.createFollow(any(FollowDto.class))).willReturn(followDto);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(followDto)));

        // then - verify the output
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.followersId", is(followDto.getFollowersId().intValue())))
                .andExpect(jsonPath("$.followingId", is(followDto.getFollowingId().intValue())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenFollowId_whenDeleteFollow_thenReturn204() throws Exception {
        // given - precondition or setup
        Long followingId = 200L;
        willDoNothing().given(followService).deleteFollow(followingId);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/follows/{id}", followingId));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }


}

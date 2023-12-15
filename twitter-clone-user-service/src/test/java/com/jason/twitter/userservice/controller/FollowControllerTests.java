package com.jason.twitter.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.service.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FollowController.class)
public class FollowControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowService followService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test for getting following list
//    @Test
//    public void whenGetFollowing_thenReturnFollowingList() throws Exception {
//        long userId = 1L;
//        List<FollowerDto> following = Arrays.asList(new FollowerDto(/* set properties */));
//        given(followService.getFollowing(userId)).willReturn(following);
//
//        mockMvc.perform(get("/api/v1/follows/{id}/following", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(following.size())))
//                .andExpect(jsonPath("$[0].someField", is(following.get(0).getSomeField())));
//    }
//
//    // Test for getting followers list
//    @Test
//    public void whenGetFollowers_thenReturnFollowersList() throws Exception {
//        long userId = 1L;
//        List<FollowerDto> followers = Arrays.asList(new FollowerDto(/* set properties */));
//        given(followService.getFollowers(userId)).willReturn(followers);
//
//        mockMvc.perform(get("/api/v1/follows/{id}/followers", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(followers.size())))
//                .andExpect(jsonPath("$[0].someField", is(followers.get(0).getSomeField())));
//    }
//
//    // Test for adding a follow
//    @Test
//    public void whenAddFollow_thenReturnFollowDto() throws Exception {
//        FollowDto followDto = new FollowDto(/* set properties */);
//        given(followService.createFollow(followDto)).willReturn(followDto);
//
//        mockMvc.perform(post("/api/v1/follows")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(followDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.someField", is(followDto.getSomeField())));
//    }

    // Test for deleting a follow
//    @Test
//    public void whenDeleteFollow_thenStatusNoContent() throws Exception {
//        long followId = 1L;
//        willDoNothing().given(followService).deleteFollow(followId);
//
//        mockMvc.perform(delete("/api/v1/follows/{id}", followId))
//                .andExpect(status().isNoContent());
//    }
}
package com.jason.twitter.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.repository.FollowRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@Sql("/data.sql")
public class FollowControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> users;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        followRepository.deleteAll();
        users = TestData.createTestUsers(10);
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenGetFollowing_thenReturnFollowingList() throws Exception {
        // given - precondition or setup
        userRepository.saveAll(users);

        Long userId = users.get(0).getId();
        List<Follow> following = TestData.createFollowing(userId, users);
        followRepository.saveAll(following);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/follows/{id}/following", userId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", is(following.size())));
    }

    @Test
    @WithMockUser("Tester")
    public void givenUserId_whenGetFollowers_thenReturnFollowersList() throws Exception {
        // given - precondition or setup
        userRepository.saveAll(users);

        Long userId = users.get(0).getId();
        List<Follow> followers = TestData.createFollowers(userId, users);
        followRepository.saveAll(followers);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/follows/{id}/followers", userId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", is(followers.size())));
    }


    @Test
    @WithMockUser("Tester")
    public void givenFollowDto_whenAddFollow_thenReturnSavedFollow() throws Exception {
        // given - precondition or setup
        FollowDto followDto = TestData.createFollowDto(new Follow(
                null,
                users.get(0).getId(),
                users.get(1).getId(),
                null
        ));

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
        Follow follow = new Follow(
                null,
                users.get(0).getId(),
                users.get(1).getId(),
                null
        );
        followRepository.save(follow);

        Long followId = follow.getId();
        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/follows/{id}", followId));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }


}

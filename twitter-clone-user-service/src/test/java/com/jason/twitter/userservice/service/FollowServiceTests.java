package com.jason.twitter.userservice.service;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.DuplicateResourceException;
import com.jason.twitter.userservice.repository.FollowRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.impl.FollowServiceImpl;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTests {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowServiceImpl followService;


    private List<User> users;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(10);
    }

    @DisplayName("JUnit test for getFollowers method")
    @Test
    public void givenUserId_whenGetFollowers_thenReturnFollowersList() {
        // given
        List<Follow> followers = TestData.createFollowers((long) 7, users);
        given(followRepository.findByFollowersId(any(Long.class))).willReturn(followers);

        // Mock userRepository.findById() for each follow object.
        followers.forEach(follow -> {
            // Find the test user from the test users list corresponding to the follow ID.
            User testUser = users.stream()
                    .filter(user -> user.getId().equals(follow.getFollowingId()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new); // Or handle with an appropriate exception.
            // Mock the findById call for the user ID.
            given(userRepository.findById(follow.getFollowingId())).willReturn(Optional.of(testUser));
        });

        // when
        List<FollowerDto> followersList = followService.getFollowers(any(Long.class));

        // then
        assertThat(followersList).isNotNull();
        assertThat(followersList.size()).isEqualTo(followers.size());
    }


    @DisplayName("JUnit test for getFollowing method")
    @Test
    public void givenUserId_whenGetFollowing_thenReturnFollowingList() {
        // given
        List<Follow> following = TestData.createFollowing((long) 7, users);
        given(followRepository.findByFollowingId(any(Long.class))).willReturn(following);

        // Mock userRepository.findById() for each follow object.
        following.forEach(follow -> {
            // Find the test user from the test users list corresponding to the follow ID.
            User testUser = users.stream()
                    .filter(user -> user.getId().equals(follow.getFollowersId()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new); // Or handle with an appropriate exception.
            // Mock the findById call for the user ID.
            given(userRepository.findById(follow.getFollowersId())).willReturn(Optional.of(testUser));
        });

        // when
        List<FollowerDto> followersList = followService.getFollowing(any(Long.class));

        // then
        assertThat(followersList).isNotNull();
        assertThat(followersList.size()).isEqualTo(following.size());
    }

    @DisplayName("JUnit test for createFollow method")
    @Test
    public void givenFollowDto_whenCreateFollow_thenReturnSavedFollowDto() {
        List<Follow> followers = TestData.createFollowers((long) 7, users);
        Follow follow = followers.get(0);
        FollowDto followDto = new FollowDto(follow.getFollowersId(), follow.getFollowingId());
        given(followRepository.save(any(Follow.class))).willReturn(follow);

        // when
        FollowDto savedFollowDto = followService.createFollow(followDto);

        // then
        assertThat(savedFollowDto).isNotNull();
    }

    @DisplayName("JUnit test for createFollow method which throws exception")
    @Test
    public void givenDuplicateFollow_whenCreateFollow_thenThrowException() {
        List<Follow> followers = TestData.createFollowers((long) 7, users);
        Follow follow = followers.get(0);
        FollowDto followDto = new FollowDto(follow.getFollowersId(), follow.getFollowingId());
        given(followRepository.save(any(Follow.class))).willThrow(new RuntimeException("Duplicate entry"));

        // when
        Assertions.assertThrows(DuplicateResourceException.class, () -> {
            followService.createFollow(followDto);
        });

        // then
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @DisplayName("JUnit test for deleteFollow method")
    @Test
    public void givenFollowId_whenDeleteFollow_thenNothing() {
        // given
        List<Follow> followers = TestData.createFollowers((long) 7, users);
        Follow follow = followers.get(0);
        given(followRepository.findById(any(Long.class))).willReturn(Optional.of(follow));
        willDoNothing().given(followRepository).deleteById(any(Long.class));

        // when
        followService.deleteFollow(follow.getId());

        // then
        verify(followRepository, times(1)).deleteById(follow.getId());
    }
}

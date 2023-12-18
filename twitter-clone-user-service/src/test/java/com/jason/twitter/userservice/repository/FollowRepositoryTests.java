package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FollowRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private List<User> users;
    private Follow follow;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(2);
        userRepository.saveAll(users);

        follow = TestData.createFollow(users.get(0), users.get(1));
        followRepository.save(follow);
    }

    // Test for findByFollowersId
    @DisplayName("JUnit test for findByFollowersId operation")
    @Test
    public void givenFollowerId_whenFindByFollowersId_thenReturnFollowList() {
        List<Follow> foundFollows = followRepository.findByFollowersId(follow.getFollowersId());

        assertThat(foundFollows).isNotEmpty();
        assertThat(foundFollows).contains(follow);
    }

    // Test for findByFollowingId
    @DisplayName("JUnit test for findByFollowingId operation")
    @Test
    public void givenFollowingId_whenFindByFollowingId_thenReturnFollowList() {
        List<Follow> foundFollows = followRepository.findByFollowingId(follow.getFollowingId());

        assertThat(foundFollows).isNotEmpty();
        assertThat(foundFollows).contains(follow);
    }

    // Add more tests as needed for other methods or edge cases
}

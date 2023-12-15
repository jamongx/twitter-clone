package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static com.jason.twitter.userservice.utils.TestData.createTestUsers;
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

        users = createTestUsers(2);
        userRepository.save(users.get(0));
        userRepository.save(users.get(1));

        follow = new Follow();
        follow.setFollowersId(users.get(0).getId());
        follow.setFollowingId(users.get(1).getId());
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

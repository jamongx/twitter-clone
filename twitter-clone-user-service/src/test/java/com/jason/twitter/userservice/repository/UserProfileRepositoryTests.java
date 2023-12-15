package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.entity.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static com.jason.twitter.userservice.utils.TestData.createTestUsers;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserProfileRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private User user;

    @BeforeEach
    public void setup() {
        // Create and set up a User entity
        List<User> users = createTestUsers(1);
        user = users.get(0);
        userRepository.save(user);
    }

    @DisplayName("JUnit test for updateUserProfileActiveStatus operation")
    @Test
    public void givenIdAndStatus_whenUpdateUserProfileActiveStatus_thenUpdateStatus() {
        Long profile_id = user.getUserProfile().getId();
        Boolean newStatus = false;

        int updatedCount = userProfileRepository.updateUserProfileActiveStatus(profile_id, newStatus);
        UserProfile updatedProfile = userProfileRepository.findById(profile_id).orElse(null);

        assertThat(updatedCount).isEqualTo(1);
        assertThat(updatedProfile).isNotNull();
        assertThat(updatedProfile.isActive()).isEqualTo(newStatus);
    }

    @DisplayName("JUnit test for updateUserProfileAvatarURL operation")
    @Test
    public void givenIdAndAvatarUrl_whenUpdateUserProfileAvatarURL_thenUpdateAvatarUrl() {
        Long profile_id = user.getUserProfile().getId();
        String newAvatarUrl = "http://example.com/new-avatar.jpg";

        int updatedCount = userProfileRepository.updateUserProfileAvatarURL(profile_id, newAvatarUrl);
        UserProfile updatedProfile = userProfileRepository.findById(profile_id).orElse(null);

        assertThat(updatedCount).isEqualTo(1);
        assertThat(updatedProfile).isNotNull();
        assertThat(updatedProfile.getAvatarUrl()).isEqualTo(newAvatarUrl);
    }

    // Add more tests as needed for other methods or edge cases
}

package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.entity.User;

import static com.jason.twitter.userservice.utils.TestData.createRegisterDto;
import static com.jason.twitter.userservice.utils.TestData.createTestUsers;
import static org.assertj.core.api.Assertions.assertThat;

import com.jason.twitter.userservice.service.AuthService;
import com.jason.twitter.userservice.service.UserService;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        // Create and set up a User entity
        List<User> users = createTestUsers(1);
        user = users.get(0);
        userRepository.save(user);
    }

    // Test for findByUsername
    @DisplayName("JUnit test for findByUsername operation")
    @Test
    public void givenUsername_whenFindByUsername_thenReturnUserObject() {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
    }

    // Test for existsByEmail
    @DisplayName("JUnit test for existsByEmail operation")
    @Test
    public void givenEmail_whenExistsByEmail_thenReturnTrue() {
        Boolean exists = userRepository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    // Test for findByUsernameOrEmail
    @DisplayName("JUnit test for findByUsernameOrEmail operation")
    @Test
    public void givenUsernameOrEmail_whenFindByUsernameOrEmail_thenReturnUserObject() {
        Optional<User> foundUserByUsername = userRepository.findByUsernameOrEmail(user.getUsername(), null);
        Optional<User> foundUserByEmail = userRepository.findByUsernameOrEmail(null, user.getEmail());

        assertThat(foundUserByUsername).isPresent();
        assertThat(foundUserByEmail).isPresent();
        assertThat(foundUserByUsername.get().getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUserByEmail.get().getEmail()).isEqualTo(user.getEmail());
    }

    // Test for existsByUsername
    @DisplayName("JUnit test for existsByUsername operation")
    @Test
    public void givenUsername_whenExistsByUsername_thenReturnTrue() {
        Boolean exists = userRepository.existsByUsername(user.getUsername());

        assertThat(exists).isTrue();
    }

    // Add more tests as needed for other methods or edge cases
}

package com.jason.twitter.userservice.repository;

import com.jason.twitter.userservice.entity.Role;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setup() {
        // Create and set up a Role entity
        role = TestData.createTestRole("ROLE_USER");
        // Save if necessary
        roleRepository.save(role);
    }

    // Test for findByRole
    @DisplayName("JUnit test for findByRole operation")
    @Test
    public void givenRole_whenFindByRole_thenReturnRoleObject() {
        Optional<Role> foundRole = Optional.ofNullable(roleRepository.findByRole(role.getRole()));

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRole()).isEqualTo(role.getRole());
    }

    // Add more tests as needed for other methods or edge cases
}

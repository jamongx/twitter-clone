package com.jason.twitter.userservice.service;

import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.UserAPIException;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.repository.RoleRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.impl.UserServiceImpl;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository; // userService's member variable

    @Mock
    private PasswordEncoder passwordEncoder;  // userService's member variable

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> users;
    private User    user;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(3);
        user = users.get(0);
        userDto = Mapper.mapToUserDto(user);
    }

    @DisplayName("JUnit test for addUser method")
    @Test
    public void givenUserObject_whenAddUser_thenReturnUserObject() {
        // given
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(userDto.getEmail())).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        UserDto savedUserDto = userService.addUser(userDto);

        // then
        assertThat(savedUserDto).isNotNull();
    }

    @DisplayName("JUnit test for addUser method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveUser_thenThrowException() {
        // given - precondition or setup
        given(userRepository.existsByEmail(userDto.getEmail())).willReturn(true);

        // when - action or the behaviour that we are going test
        Assertions.assertThrows(UserAPIException.class, () -> {
            userService.addUser(userDto);
        });

        // then
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for addUser method which throws exception")
    @Test
    public void givenExistingUsername_whenSaveUser_thenThrowException() {
        // given - precondition or setup
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(true);

        // when - action or the behaviour that we are going test
        Assertions.assertThrows(UserAPIException.class, () -> {
            userService.addUser(userDto);
        });

        // then
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for getUser method")
    @Test
    public void givenUserId_whenFindById_thenReturnUserObject() {
        // given
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        UserDto savedUserDto = userService.getUser(user.getId());

        // then
        assertThat(savedUserDto).isNotNull();
    }

    // JUnit test for FindAll method
    @DisplayName("JUnit test for FindAll method (negative scenario)")
    @Test
    public void givenEmptyUserDtosList_whenFindAll_thenReturnEmptyUserDtosList() {
        // given - precondition or setup
        given(userRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        List<UserDto> userDtosList = userService.findAll();

        // then - verify the output
        assertThat(userDtosList).isEmpty();
        assertThat(userDtosList.size()).isEqualTo(0);
    }

    // JUnit test for findAll method
    @DisplayName("JUnit test for FindAll method")
    @Test
    public void givenUsersList_whenFindAll_thenReturnUsersList() {
        // given
        given(userRepository.findAll()).willReturn(users);

        // when - action or the behaviour that we are going test
        List<UserDto> userDtos = userService.findAll();

        // then - verify the output
        assertThat(userDtos).isNotNull();
        assertThat(userDtos.size()).isEqualTo(users.size());
    }

    // JUnit test for updatedEmployee method
    @DisplayName("JUnit test for updateUser method")
    @Test
    public void givenUserDtoObject_whenUpdatedUser_thenReturnUpdatedUserDto() {
        // given - precondition or setup
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        userDto.setUsername("jason");
        userDto.setEmail("jason@gmail.com");

        // when - action or the behaviour that we are going test
        UserDto updatedUserDto = userService.updateUser(userDto, user.getId());

        // then - verify the output
        assertThat(updatedUserDto.getUsername()).isEqualTo("jason");
        assertThat(updatedUserDto.getEmail()).isEqualTo("jason@gmail.com");
    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing() {
        // given - precondition or setup
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        willDoNothing().given(userRepository).deleteById(user.getId());

        // when - action or the behaviour that we are going test
        userService.deleteById(user.getId());

        // then - verify the output
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}

package com.jason.twitter.userservice.service;

import com.jason.twitter.userservice.constants.SecurityConstants;
import com.jason.twitter.userservice.dto.JwtAuthResponse;
import com.jason.twitter.userservice.dto.LoginDto;
import com.jason.twitter.userservice.dto.RegisterDto;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.UserAPIException;
import com.jason.twitter.userservice.repository.RoleRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.security.JwtTokenProvider;
import com.jason.twitter.userservice.service.impl.AuthServiceImpl;
import com.jason.twitter.userservice.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    // it's needed for AuthService.register()
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private List<User> users;

    @BeforeEach
    public void setup() {
        users = TestData.createTestUsers(1);
    }

    // Test for register method
    @DisplayName("JUnit test for register method")
    @Test
    public void givenRegisterDto_whenRegister_thenReturnSuccessMessage() {
        RegisterDto registerDto = TestData.createRegisterDto(users.get(0));

        given(userRepository.existsByUsername(registerDto.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(registerDto.getEmail())).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(users.get(0));

        String response = authService.register(registerDto);
        assertThat(response).isEqualTo("User Registered Successfully!");
    }

    // Test for register method when user already exists
    @DisplayName("JUnit test for register method when user already exists")
    @Test
    public void givenExistingUser_whenRegister_thenThrowUserAPIException() {
        RegisterDto registerDto = TestData.createRegisterDto(users.get(0));
        given(userRepository.existsByUsername(registerDto.getUsername())).willReturn(true);
        try {
            authService.register(registerDto);
        } catch (UserAPIException ex) {
            assertThat(ex.getMessage()).isEqualTo("Username already exists!");
        }
    }

    @DisplayName("JUnit test for login method")
    @Test
    public void givenLoginDto_whenLogin_thenReturnJwtAuthResponse() {
        // Setting up mock objects:
        // Create mock Authentication object and configure the AuthenticationManager.
        // When called, AuthenticationManager should return the mock Authentication object.
        Authentication authentication = mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);

        // Set the result of the authentication process (i.e., the Authentication object)
        // in the Spring Security's SecurityContext.
        // This stores the authenticated user's information and allows access to it in other parts of the application.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Configure tokenProvider.generateToken to return a "mock.jwt.token" string when called.
        String mockJwtToken = "mock.jwt.token";
        given(tokenProvider.generateToken(authentication)).willReturn(mockJwtToken);

        // Mocking user retrieval.
        // Instead of authService.getUserByUsernameOrEmail (which is private and cannot be directly mocked),
        // userRepository.findByUsernameOrEmail is configured to return a predefined user object.
        User user = users.get(0);
        given(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .willReturn(Optional.of(user));

        // when - action or the behaviour that we are going to test.
        LoginDto loginDto = new LoginDto(user.getUsername(), user.getPassword());
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);

        // then - verify the output.
        assertThat(jwtAuthResponse).isNotNull();
        assertThat(jwtAuthResponse.getAccessToken()).isEqualTo(mockJwtToken);
        assertThat(jwtAuthResponse.getTokenType()).isEqualTo(SecurityConstants.BEARER_TOKEN_TYPE);
    }



    // Additional tests can be added as needed for other methods or edge cases

}

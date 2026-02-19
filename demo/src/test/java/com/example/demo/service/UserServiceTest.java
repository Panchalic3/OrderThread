package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User userInput;

    @BeforeEach
    void setUp() {
        userInput = new User();
        userInput.setId(1L);
        userInput.setUsername("diva");
        userInput.setPassword("plainPassword");
    }

    @Test
    void shouldCreateUserAndEncodePassword() {

        // Given
        when(passwordEncoder.encode("plainPassword"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(userInput);

        // When
        User result = userService.createUser(userInput);

        // Then
        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(userInput);

        assertNotNull(result);
        assertEquals(" ", result.getPassword()); // password cleared
    }
}

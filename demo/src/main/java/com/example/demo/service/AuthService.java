package com.example.demo.service;

import com.example.demo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // added as a part of step 6 for jwt generation upon successful login
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean passwordMatched =
                passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatched) {
            throw new RuntimeException("Invalid credentials");
        }

        /* added as a part of step 6 for jwt generation upon successful login,
         prior to this we were returning a success msg string
         */
        return jwtUtil.generateToken(user.getUsername());
    }
}

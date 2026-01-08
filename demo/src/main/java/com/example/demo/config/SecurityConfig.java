package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// disableing the csrf
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        /** sessionManagement -> A session is Server-side memory -> You login with username + password
         Server creates a session - Session ID stored in cookie - Browser sends cookie on every request
         *
         * STATELESS = server remembers NOTHING - No session stored on server -No session ID -No server memory per user
         * Every request is independent
         * */

        /* think authorizeHttpRequests as gate keeper, in the current scenario,
        it only allows /auth/login, all other things needs to be authenticated
        * */
//                        .authorizeHttpRequests(auth -> auth.anyRequest()
//                .permitAll()); -> This line permits all

        return http.build();
    }
//so that user passwords can be securely encrypted before
// storing and later matched during authentication.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

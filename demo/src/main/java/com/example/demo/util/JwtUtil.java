package com.example.demo.util;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    /**
     * HEADER.PAYLOAD.SIGNATURE
     *
     * Jwts(JSON Web Tokens(plural)) is a utility class provided by the
     * JJWT(Java JSON Web Token) library for creating and parsing JSON Web Tokens.
     *
     * Parsing a token means reading it, checking it, and extracting information from it.
     *
     * Jwts.builder() is used to generate JWT tokens, and Jwts.parserBuilder()
     * is used to validate and parse them.
     * **/

    // secret key used for signing token
    //creates a secure SecretKey for HMAC-based JWT signing and ensures the key length is cryptographically safe.
    private final SecretKey secretKey = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey12345".getBytes());
    // token validity (5 minutes)
    private final long jwtExpirationMs = 5 * 60 * 1000;

    //Generate JWT Token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()// jwts - JSON Web Tokens(plural)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // It reads the JWT, verifies it, and returns all the data (claims) stored inside it.
    //invalid ,expired, tampered -this method throws an exception.

    /** claim means Something that is asserted to be true, so the token is
     * claiming the user and its role, issue time and expire time */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)// Use THIS secret key to verify the token signature.
                .build()
                .parseClaimsJws(token)// Verifies the signature ,Checks expiration time ,Validates token structure
                .getBody(); //-> The payload of the JWT (called Claims)
    }
}

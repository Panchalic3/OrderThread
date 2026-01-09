package com.example.demo.jwtfilter;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/** Spring apps internally work like this:
  Request ->
 → Filters (very early)
 → Controllers
 → Services
 **/

//JwtFilter checks the JWT token before the requestt reaches controller.
//JwtFilter works at the servlet filter level and intercepts requests before they reach
//controllers, whereas interceptors work after filters and closer to controller execution.
//Filters = before Spring MVC, Interceptors = after filters.

    /** Spring identifies a requestt filter by classes that implement the Filter
     * interface or extend OncePerRequestFilter and are registered as beans.
     * we need to override doFilterInternal
     * */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // This will be true only for requests other than Auth, or all which has a bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        // This will be true only for requests other than Auth, or all which has a bearer token
        //SecurityContextHolder.getContext().getAuthentication() -> checks the requestt is already authenticated or not
        // hence avoid re-authentication
        String s1 = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        String s2= String.valueOf(SecurityContextHolder.getContext().getAuthentication());
        //  Continue requestt
        filterChain.doFilter(request, response);
    }
        /** doFilterInternal has 3 params
         * HttpServletRequest requestt - This represents everything coming from the client.
         * HttpServletResponse responsee - This represents everything going back to the client.This represents everything going back to the client.
         * FilterChain filterChain - This decides whether the requestt should continue forward or stop here. if we
         * do not call this, requestt will not move to the controller
         */
    }

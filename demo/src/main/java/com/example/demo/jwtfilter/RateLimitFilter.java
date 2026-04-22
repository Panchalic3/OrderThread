package com.example.demo.jwtfilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
// step 2 for rate limit
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.simple(10, Duration.ofMinutes(1))) // 10 req/min
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String key = getClientKey(request);

        Bucket bucket = cache.computeIfAbsent(key, k -> createNewBucket());
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response); // allow
        } else {
            response.setStatus(429);
            response.getWriter().write("Too many requests"); // block
        }

    }
    private String getClientKey(HttpServletRequest request) {
        return request.getRemoteAddr(); // IP-based limiting
    }
}

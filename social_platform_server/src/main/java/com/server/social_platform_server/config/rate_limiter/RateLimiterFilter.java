package com.server.social_platform_server.config.rate_limiter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterFilter implements Filter {
    private final Map<String, List<Long>> attempts = new ConcurrentHashMap<>();

    private final static int MAX_ATTEMPTS = 5; //5 requests max
    private final static long TIME_WINDOW_MS = 30 * 1000; // 30 * 1000 ms = 30 s


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String ip = httpServletRequest.getRemoteAddr();

        if(httpServletRequest.getRequestURI().equals("/api/auth/login") && httpServletRequest.getMethod().equalsIgnoreCase("POST")){
            long now = Instant.now().toEpochMilli();
            attempts.putIfAbsent(ip, new ArrayList<>());
            List<Long> timestamps = attempts.get(ip);

            timestamps.removeIf(timestamp -> now - timestamp > TIME_WINDOW_MS);

            if(timestamps.size() >= MAX_ATTEMPTS){
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.setStatus(429);
                httpServletResponse.getWriter().write("Too many login attempts, please try again later");
            }

            timestamps.add(now);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

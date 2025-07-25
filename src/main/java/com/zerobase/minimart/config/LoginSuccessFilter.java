package com.zerobase.minimart.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginSuccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 루트(/)로 접근할 때만 처리 (index.html)
        if (request.getRequestURI().equals("/")) {
            request.getSession().removeAttribute("errorMessage");
        }

        // 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}
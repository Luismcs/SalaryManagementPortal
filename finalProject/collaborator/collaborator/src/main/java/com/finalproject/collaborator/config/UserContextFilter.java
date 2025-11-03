package com.finalproject.collaborator.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserContextFilter extends OncePerRequestFilter {

    public final UserContext userContext;

    public UserContextFilter(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("X-Authenticated-User") != null) {
            userContext.setCurrentUser(request.getHeader("X-Authenticated-User"));
        }

        filterChain.doFilter(request, response);
        userContext.clear();
    }

}

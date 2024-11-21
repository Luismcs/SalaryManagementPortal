package com.finalproject.portal.config;

import com.finalproject.portal.service.JwtServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;

    public JwtFilter(JwtServiceImpl jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        List<String> roles = new ArrayList<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtService.extractUsername(token);
            roles = jwtService.extractRoles(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!jwtService.isTokenExpired(token)) {

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authToken.setDetails(Map.of("token", token)); // Guarda o token no contexto para dser acessado mais
                // tarde
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

}

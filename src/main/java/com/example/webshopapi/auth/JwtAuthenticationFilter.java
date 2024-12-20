package com.example.webshopapi.auth;

import com.example.webshopapi.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authToken = extractAuthTokenFromCookie(request);

        if (authToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenService.validateToken(authToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        handleAuthentication(authToken, request);

        filterChain.doFilter(request, response);
    }

    private String extractAuthTokenFromCookie(@NonNull HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void handleAuthentication(String authToken, @NonNull HttpServletRequest request) {

        String username = jwtTokenService.getUsernameFromToken(authToken);
        String role = jwtTokenService.getRoleFromToken(authToken);

        if (username == null || role == null) {
            return;
        }

        String formattedRole = "ROLE_" + role.toUpperCase();
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(formattedRole));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                grantedAuthorities
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

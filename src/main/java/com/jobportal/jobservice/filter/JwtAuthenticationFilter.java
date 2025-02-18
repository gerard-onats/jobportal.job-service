package com.jobportal.jobservice.filter;

import com.jobportal.jobservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader("Authorization");
        if(!jwtUtils.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(token);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader("Authorization"));
        return authorizationHeader.isPresent();
    }

    public void setAuthenticationContext(String token) {
        Claims tokenClaims = jwtUtils.getClaims(token);
        String principal = tokenClaims.getSubject();

        /* TODO automate this in claims, extract and for loop */
        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_USER");
        UsernamePasswordAuthenticationToken contextToken = new UsernamePasswordAuthenticationToken(principal, null, List.of(authorities));

        SecurityContextHolder.getContext().setAuthentication(contextToken);
    }
}

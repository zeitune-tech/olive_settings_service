package sn.zeitune.oliveinsurancesettings.security;


import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import sn.zeitune.oliveinsurancesettings.client.UserRole;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final TokenDecoderService tokenDecoderService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Skip authentication for auth endpoints
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (tokenDecoderService.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            UserRole userType = request.getHeader("X-User-Type") != null ?
            UserRole.valueOf(request.getHeader("X-User-Type")) : null;

            String username = tokenDecoderService.getUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (tokenDecoderService.isExpired(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.info("Authentication successful for username: {}", username);
                } else {
                    log.warn("JWT validation failed for username: {}", username);
                    throw new JwtException("Invalid token");
                }
            }

        } catch (JwtException | IllegalArgumentException ex) {
            log.error("JWT Processing Error: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Unauthorized\", \"message\": \"" + ex.getMessage() + "\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}

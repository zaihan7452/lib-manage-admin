package com.hanzai.app.security;

import com.hanzai.app.constant.LibManageAdminConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Filter for JWT authentication.
 * This filter intercepts HTTP requests to validate JWT tokens and set authentication in the context.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Intercepts and filters HTTP requests to validate JWT tokens.
     *
     * @param request     incoming HTTP request
     * @param response    HTTP response
     * @param filterChain filter chain to continue processing
     * @throws ServletException in case of servlet error
     * @throws IOException      in case of I/O error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isBlank(authHeader)) {
                log.error("JWT token is missing");
                filterChain.doFilter(request, response);
                return;
            }

            if (!StringUtils.startsWith(authHeader, LibManageAdminConstant.BEARER)) {
                log.error("JWT token is not a Bearer token");
                filterChain.doFilter(request, response);
                return;
            }

            String token = StringUtils.removeStart(authHeader, LibManageAdminConstant.BEARER).trim();
            if (!jwtUtil.validateToken(token)) {
                log.error("JWT token is expired or invalid");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.getUsername(token);
            if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("JWT authentication failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getRequestURI().contains("/v1/auth/login")) {
            return true;
        }

        if (request.getRequestURI().contains("/v1/github/oauth")) {
            return true;
        }

        return request.getRequestURI().contains("/swagger-ui") ||
                request.getRequestURI().contains("/v3/api-docs") ||
                request.getRequestURI().contains("/openapi.json");
    }
}

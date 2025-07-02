package org.example.hamlol.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // JwtTokenProvider를 주입받아 JWT 관련 로직을 활용할 수 있도록 합니다.
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final String[] WHITELIST = {
            "/api/adduser",
            "/api/login",
            "/swagger-ui",
            "/v3/api-docs",
            "/favicon.ico",
            "/error"
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ 화이트리스트 경로는 필터 로직을 건너뜀
        String path = request.getRequestURI();
        for (String uri : WHITELIST) {
            if (path.startsWith(uri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // HTTP 요청의 Authorization 헤더에서 "Bearer " 접두어를 제거한 토큰을 추출합니다.
        String token = jwtTokenProvider.resolveToken(request);

        // 토큰이 존재하고 유효한 경우, 해당 토큰으로부터 Authentication 객체를 생성하고
        // SecurityContext에 저장합니다.
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청을 전달합니다.
        filterChain.doFilter(request, response);
    }
}
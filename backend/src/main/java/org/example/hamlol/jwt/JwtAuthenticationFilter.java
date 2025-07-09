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
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // 필터를 건너뛸 퍼블릭 URL 목록
    private static final List<String> PUBLIC_URLS = List.of(
            "/api/adduser",
            "/api/login",
            "/swagger-ui/",
            "/v3/api-docs/"
    );

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 퍼블릭 URL일 경우, doFilterInternal() 자체를 호출하지 않음.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return PUBLIC_URLS.contains(path);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("🔹 [JwtFilter] 요청 경로: " + path);

        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("🔹 [JwtFilter] 추출된 토큰: " + token);

        // 토큰이 있고 유효하다면 인증 처리
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("✅ [JwtFilter] 인증 완료: " + authentication.getName());
        } else {
            System.out.println("⚠️ [JwtFilter] 인증되지 않은 요청: " + path);
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}

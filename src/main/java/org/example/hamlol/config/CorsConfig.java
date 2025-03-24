package org.example.hamlol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 모든 Origin 허용 (개발용)
        config.addAllowedHeader("*");        // 모든 헤더 허용
        config.addAllowedMethod("*");        // 모든 HTTP 메서드 허용
        config.setAllowCredentials(true);    // 쿠키 or 인증 정보 허용 (JWT 필요 시 필수)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 적용

        return new CorsFilter(source);
    }
}

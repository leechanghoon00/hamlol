package org.example.hamlol.config;

import org.example.hamlol.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // UserService가 UserDetailsService를 구현하므로, 이를 주입받습니다.
    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userDetailsService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (개발 단계에서는 비활성화, 운영 시 활성화 권장)
                .csrf(AbstractHttpConfigurer::disable)
                // URL 접근권한 설정
                .authorizeHttpRequests(authz -> authz
                        // 로그인, 회원가입, 정적 리소스 등은 인증 없이 접근
                        .requestMatchers("/api/**","/**.html","/login.html","/api/login", "/api/adduser", "/signup.html", "/static/**", "/favicon", "/error")
                        .permitAll()
                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )


                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login.html")        // 로그아웃 후 이동할 페이지
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

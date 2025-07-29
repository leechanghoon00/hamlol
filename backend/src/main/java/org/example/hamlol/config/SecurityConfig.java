package org.example.hamlol.config;

import jakarta.servlet.Filter;
import org.example.hamlol.jwt.JwtAuthenticationFilter;
import org.example.hamlol.jwt.JwtTokenProvider;
import org.example.hamlol.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
private final org.example.hamlol.config.CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    public SecurityConfig(@Lazy UserService userService, JwtTokenProvider jwtTokenProvider, org.example.hamlol.config.CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.userDetailsService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // CorsFilter 자동 적용
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/adduser").permitAll()
                        // 로그인도 누구나
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                        .requestMatchers(
                                "/api/reset-password",
                                "/api/send-reset-password",
                                "/adduser",
                                "/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/favicon.ico",
                                "/login.html",
                                "/signup.html",
                                "/static/**",
                                "/favicon",
                                "/error",
                                "/",
                                "/index.html"
                        ).permitAll()
                        .requestMatchers("/download/**", "/get.php", "/admin/**").denyAll()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(customAuthenticationEntryPoint)) // ✅ 핵심
                .logout(logout -> logout.logoutSuccessUrl("/login.html").invalidateHttpSession(true).permitAll())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}

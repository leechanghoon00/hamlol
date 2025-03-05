//package org.example.hamlol.config;
//
//import org.example.hamlol.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
////    private final UserService userService;
////
////    @Autowired
////    public SecurityConfig(UserService userService) {
////        this.userService = userService;
////    }
////
////    // SecurityFilterChain 설정: HTTP 요청에 대한 보안 설정
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        return http
////                .authorizeRequests()
////                .antMatchers("/register", "/login", "/css/**", "/static/**", "/favicon", "/error").permitAll() // 회원가입, 로그인 페이지는 인증 없이 접근 가능
////                .anyRequest().authenticated() // 나머지 경로는 인증 필요
////                .formLogin()
////                .loginPage("/login") // 로그인 페이지 설정
////                .defaultSuccessUrl("/home", true) // 로그인 성공 후 리다이렉트할 페이지
////                .and()
////                .logout()
////                .logoutSuccessUrl("/login") // 로그아웃 후 리다이렉트할 페이지
////                .invalidateHttpSession(true) // 로그아웃 후 세션 삭제
////                .and()
////                .csrf().disable() // CSRF 보호 비활성화 (필요시 활성화)
////                .build();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
////        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
////        daoAuthenticationProvider.setUserDetailsService(userService);
////        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 암호화 설정
////        return http.getSharedObject(AuthenticationManagerBuilder.class)
////                .authenticationProvider(daoAuthenticationProvider).build();
////    }
////
////    private PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////
////    }
//
//}

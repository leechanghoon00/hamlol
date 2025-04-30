package org.example.hamlol.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.hamlol.service.CustomDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    // Access Token 유효기간: 30분, Refresh Token 유효기간: 1주 (밀리초 단위)
    private final long THIRTY_MINUTES = 1000 * 60 * 180;
    private final long ONE_WEEK = 1000 * 60 * 60 * 24 * 7;
    private final CustomDetailService customDetailService;
    // application.properties 또는 application.yml에 등록된 jwt.secret 값을 주입받아 Key 객체를 생성합니다.
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, CustomDetailService customDetailService) {
        this.customDetailService = customDetailService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenInfo generateToken(Collection<? extends GrantedAuthority> authorityInfo, String email,String gameName,String tagLine) {
        String authorities = authorityInfo.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = System.currentTimeMillis();

        // Access Token 생성 (유효기간: 30분)
        Date accessTokenExpiresIn = new Date(now + THIRTY_MINUTES);
        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("auth", authorities)
                .claim("gameName",gameName)
                .claim("tagLine",tagLine)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 (유효기간: 1주) – Payload에 추가 정보는 담지 않습니다.
        Date refreshTokenExpiresIn = new Date(now + ONE_WEEK);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("JWT 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 사용자 아이디(subject)를 추출합니다.
    public String getUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

        public Authentication getAuthentication(String token) {
            // 토큰을 객체로 바꿈
            Claims claims = parseClaims(token);
            // 그안에서 email을 꺼냄
            String email = claims.getSubject();
            // 꺼낸 email로 db를 조회해서 롤닉과 태그 꺼내서 저장
            UserDetails userDetails = customDetailService.gameNamebyemail(email);
            // 저장
            return new UsernamePasswordAuthenticationToken(userDetails,token,userDetails.getAuthorities());
        }

    // 내부: 토큰 파싱. 만료된 토큰의 경우 ExpiredJwtException을 발생시킵니다.
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.", e);
        }
    }

    // HTTP 요청의 "Authorization" 헤더에서 Bearer 토큰을 추출합니다.
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization 헤더: {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") && bearerToken.length() > 7) {
            return bearerToken.substring(7);
        }
        return null;
    }


}

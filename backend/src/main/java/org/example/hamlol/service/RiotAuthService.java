package org.example.hamlol.service;

import org.example.hamlol.dto.RiotAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;
import java.util.HashMap;

@Service
public class RiotAuthService {

    @Value("${riot.client.id}")
    private String clientId;

    @Value("${riot.client.secret}")
    private String clientSecret;

    @Value("${riot.redirect.uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public RiotAuthResponse processRiotCallback(String code, String state) {
        try {
            // 1. Authorization code를 access token으로 교환
            String accessToken = exchangeCodeForToken(code);
            
            // 2. Access token으로 사용자 정보 조회
            Map<String, Object> userInfo = getUserInfo(accessToken);
            
            // 3. 사용자 정보에서 Riot ID 추출
            String riotId = extractRiotId(userInfo);
            String summonerName = extractSummonerName(userInfo);
            
            // 4. JWT 토큰 생성 (기존 인증 시스템과 연동)
            String jwtToken = generateJwtToken(riotId, summonerName);
            
            return RiotAuthResponse.builder()
                    .success(true)
                    .message("Riot 인증 성공")
                    .accessToken(jwtToken)
                    .riotId(riotId)
                    .summonerName(summonerName)
                    .build();
                    
        } catch (Exception e) {
            throw new RuntimeException("Riot 인증 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    private String exchangeCodeForToken(String code) {
        String tokenUrl = "https://auth.riotgames.com/token";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        
        String body = String.format(
            "grant_type=authorization_code&code=%s&redirect_uri=%s",
            code, redirectUri
        );
        
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        
        throw new RuntimeException("토큰 교환 실패");
    }

    private Map<String, Object> getUserInfo(String accessToken) {
        String userInfoUrl = "https://auth.riotgames.com/userinfo";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
            userInfoUrl, HttpMethod.GET, request, Map.class
        );
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }
        
        throw new RuntimeException("사용자 정보 조회 실패");
    }

    private String extractRiotId(Map<String, Object> userInfo) {
        // Riot 사용자 정보에서 Riot ID 추출
        if (userInfo.containsKey("sub")) {
            return (String) userInfo.get("sub");
        }
        throw new RuntimeException("Riot ID를 찾을 수 없습니다");
    }

    private String extractSummonerName(Map<String, Object> userInfo) {
        // Riot 사용자 정보에서 소환사명 추출
        if (userInfo.containsKey("preferred_username")) {
            return (String) userInfo.get("preferred_username");
        }
        return "Unknown";
    }

    private String generateJwtToken(String riotId, String summonerName) {
        // 기존 JWT 토큰 생성 로직과 연동
        // 여기서는 간단한 예시로 구현
        // 실제로는 기존 UserService나 JwtService를 사용해야 함
        
        // 임시로 간단한 토큰 생성 (실제 구현 시 수정 필요)
        return "temp_jwt_token_" + riotId + "_" + System.currentTimeMillis();
    }
}




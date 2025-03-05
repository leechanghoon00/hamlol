package org.example.hamlol.controller;

import org.example.hamlol.dto.AccountDto;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RestTemplate restTemplate;  // RestTemplate 주입

    @PostMapping("/save")
    public ResponseEntity<String> saveAccount(@RequestBody AccountDto accountDto,
                                              @RequestParam(value = "api_key", required = false) String apiKey) {

        // tagLine에 있는 #을 URL 인코딩 처리
        String encodedTagLine = URLEncoder.encode(accountDto.tagLine(), StandardCharsets.UTF_8);

        // Riot API URL을 동적으로 생성
        String url = UriComponentsBuilder.fromHttpUrl("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id")
                .pathSegment(accountDto.gameName(), encodedTagLine)  // 인코딩된 tagLine을 사용
                .queryParam("api_key", apiKey != null ? apiKey : "라이엇API킨")
                .toUriString();

        // RestTemplate을 사용하여 Riot API 호출
        AccountDto account = null;
        try {
            account = restTemplate.getForObject(url, AccountDto.class); // Riot API에서 응답을 받아 AccountDto로 변환
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Riot API 호출 중 오류 발생: " + e.getMessage());
        }

        // 응답이 없으면 404 반환, 아니면 정상 데이터 반환
        if (account == null) {
            return ResponseEntity.status(404).body("Account not found");
        }

        // 받은 데이터를 데이터베이스에 저장
        accountService.saveAccount(account);

        // 저장 완료 후 메시지 반환
        return ResponseEntity.ok("소환사 정보가 저장되었습니다.");
    }
}

package org.example.hamlol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/riot/account/v1/accounts/**")
public class RiotAccountController {


//    private static final String API_BASE_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id";

    // 라이엇 ID로 계정 조회
//    @GetMapping("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
//    public ResponseEntity<AccountDto> getAccountByRiotI(@PathVariable String gameName,
//                                                        @PathVariable String tagLine,
//                                                        @RequestParam(value = "api_key", required = false) String apiKey){
//
//        // Riot API의 URL을 동적으로 생성
//        String url = UriComponentsBuilder.fromHttpUrl("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id")
//                .pathSegment(gameName, tagLine)
//                .queryParam("api_key", apiKey != null ? apiKey : "라이엇 API 키")
//                .toUriString();
//
//        // RestTemplate을 사용하여 Riot API 호출
//        RestTemplate restTemplate = new RestTemplate();
//        AccountDto account = restTemplate.getForObject(url, AccountDto.class);
//
//        // 응답이 null이면 404 반환, 아니면 정상 데이터 반환
//        if (account == null) {
//            return ResponseEntity.status(404).body(null); // 예시로 404 반환, 예외 처리 추가 가능
//        }
//
//        // 정상적으로 조회된 AccountDto 반환
//        return ResponseEntity.ok(account);
//    }


/*
    // puuid로 계정 조회
    @GetMapping("/riot/account/v1/accounts/by-puuid/{puuid}")
    public ResponseEntity<AccountDto> getAccountBypuuid(@PathVariable String puuid,
                                                        @RequestParam(value = "api_kye", required = false)String apiKey) {

    }



    // puuid로 챔피언 숙련도 조회
    @GetMapping("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}")
    public  ResponseEntity<List<ChampionMasteryDto>> getChampionMasteries(){

    }*/





}

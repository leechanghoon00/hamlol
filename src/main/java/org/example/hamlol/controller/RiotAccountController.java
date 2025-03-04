package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.hamlol.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/riot/account/v1/accounts/**")
public class RiotAccountController {

    // 라이엇 ID로 계정 조회
    @GetMapping("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    public ResponseEntity<AccountDto> getAccountByRiotI(@PathVariable String gameName,
                                                        @PathVariable String tagLine,
                                                        @RequestParam(value = "api_key", required = false) String apiKey){
        AccountDto account = new AccountDto();
        account.setGameName(gameName);
        account.setTagLine(tagLine);
        return ResponseEntity.ok(account);

    }

/*    // puuid로 계정 조회
    @GetMapping("/riot/account/v1/accounts/by-puuid/{puuid}")
    public ResponseEntity<AccountDto> getAccountBypuuid(){

    }

    // puuid로 챔피언 숙련도 조회
    @GetMapping("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}")
    public  ResponseEntity<List<ChampionMasteryDto>> getChampionMasteries(){

    }*/





}

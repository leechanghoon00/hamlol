package org.example.hamlol.controller;

import org.example.hamlol.dto.AccountDto;
import org.example.hamlol.dto.ChampionMasteryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RiotAccountController {

    @GetMapping("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    public ResponseEntity<AccountDto> getAccountByRiotI(){

    }

    @GetMapping("/riot/account/v1/accounts/by-puuid/{puuid}")
    public ResponseEntity<AccountDto> getAccountBypuuid(){

    }

    @GetMapping("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}")
    public  ResponseEntity<List<ChampionMasteryDto>> getChampionMasteries(){

    }





}

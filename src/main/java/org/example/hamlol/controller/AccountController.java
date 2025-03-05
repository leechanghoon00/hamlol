package org.example.hamlol.controller;

import org.example.hamlol.dto.AccountDto;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<String> saveAccount(@RequestBody AccountDto accountDto) {
        try {
            // 1. Riot API에서 소환사 정보 조회
            AccountDto accountInfo = accountService.AccountInfo(accountDto.gameName(), accountDto.tagLine());
            // 2. DB에 저장
            accountService.saveAccount(accountInfo);

            return ResponseEntity.ok("Account saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

package org.example.hamlol.controller;

import org.example.hamlol.dto.AccountRequestDto;
import org.example.hamlol.dto.AccountResponseDTO;
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
    public ResponseEntity<String> saveAccount(@RequestBody AccountRequestDto accountRequestDto) {
        try {
            // 1. Riot API에서 소환사 정보 조회+ 저장
            AccountResponseDTO saveAccount = accountService.getAccountInfoAndSaveAccount((accountRequestDto));

            return ResponseEntity.ok("Account saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

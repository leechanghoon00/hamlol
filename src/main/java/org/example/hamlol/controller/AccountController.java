package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.dto.AccountRequestDTO;
import org.example.hamlol.dto.AccountResponseDTO;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@Tag(name = "AccountService API", description = "롤아이디와 태그를이용해 puuid 저장")

public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<String> saveAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            // 1. Riot API에서 소환사 정보 조회+ 저장
            AccountResponseDTO saveAccount = accountService.getAccountInfoAndSaveAccount((accountRequestDTO));

            return ResponseEntity.ok("저장성공!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("실패 : " + e.getMessage());
        }
    }
}

package org.example.hamlol.controller;

import org.example.hamlol.dto.RiotAuthRequest;
import org.example.hamlol.dto.RiotAuthResponse;
import org.example.hamlol.service.RiotAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/riot")
@CrossOrigin(origins = "*")
public class RiotAuthController {

    @Autowired
    private RiotAuthService riotAuthService;

    @PostMapping("/callback")
    public ResponseEntity<RiotAuthResponse> handleRiotCallback(@RequestBody RiotAuthRequest request) {
        try {
            RiotAuthResponse response = riotAuthService.processRiotCallback(request.getCode(), request.getState());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RiotAuthResponse.builder()
                    .success(false)
                    .message("Riot 인증 처리 실패: " + e.getMessage())
                    .build());
        }
    }
}




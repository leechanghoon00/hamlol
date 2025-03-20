package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;
import org.example.hamlol.service.SaveGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
@Tag(name = "Game API", description = "게임저장API")

@Controller
@RequestMapping("/api/game")
public class SaveGameController {

    @Autowired
    private SaveGameService saveGameService;

    @Operation(summary = "게임 저장", description = "매치 정보, 팀 정보, 플레이어 정보를 저장합니다.")

    @PostMapping("/save")
    public ResponseEntity<String> saveGame(@RequestBody MatchDTO matchDTO) {
        try {
            // 팀, 플레이어 정보는 테스트 목적으로 빈 리스트로 전달
            saveGameService.saveGame(matchDTO, Collections.emptyList(), Collections.emptyList());
            return ResponseEntity.ok("Game saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
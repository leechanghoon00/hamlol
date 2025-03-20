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
    public ResponseEntity<String> saveGame() {
// 테스트용 matchId 설정
        String testMatchId = "KR_7565284912";
        // 임의의 데이터로 MatchDTO 생성 (필요한 다른 필드는 null 또는 기본값으로 설정)
        MatchDTO matchDTO = new MatchDTO(testMatchId, null, null, null);
        // 팀, 플레이어 정보는 빈 리스트로 전달 (URL 연결 확인 목적)
        saveGameService.saveGame(matchDTO, Collections.emptyList(), Collections.emptyList());
        return ResponseEntity.status(HttpStatus.OK).body("URL 연결 테스트 완료");
    }
}
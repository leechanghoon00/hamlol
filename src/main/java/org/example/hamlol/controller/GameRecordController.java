package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.SimpleGameDTO;
import org.example.hamlol.service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRecordController {

    @Autowired
    private GameRecordService gameRecordService;



    @Operation(summary = "전적 상세보기",description = "상세 전적 불러옴")
@GetMapping("/bymatchid")
    public List<GameRecordDTO> gameRecordByMatchId(@RequestParam("matchId") String matchId){
    return gameRecordService.gameRecordByMatchId(matchId);
}
    @Operation(summary = "전적 불러오기",description = "롤닉과 태그로 전적 불러옴")

@GetMapping("/bygameid")
    public List<SimpleGameDTO> gameRecordByGameId(@RequestParam("riotIdGameName") String riotIdGameName, @RequestParam("riotidtagline") String riotidtagline){
    return gameRecordService.gameRecordByGameId(riotIdGameName,riotidtagline);
}

}

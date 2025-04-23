package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.GameRecordRequest;
import org.example.hamlol.dto.SimpleGameDTO;
import org.example.hamlol.service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
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
@PostMapping("/bygameid")
    public Page<SimpleGameDTO> gameRecordByGameId(@RequestBody GameRecordRequest gameRecordRequest,
            Pageable pageable) {

        return gameRecordService.gameRecordByGameId(
                gameRecordRequest.getRiotIdGameName(),
                gameRecordRequest.getRiotIdTagline(),
                pageable);

}



}

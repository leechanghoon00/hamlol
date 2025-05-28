package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.GameRecordRequest;
import org.example.hamlol.dto.SimpleGameDTO;
import org.example.hamlol.jwt.CustomUser;
import org.example.hamlol.service.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class GameRecordController {

    @Autowired
    private GameRecordService gameRecordService;



    @Operation(summary = "전적 상세보기",description = "상세 전적 불러옴")
@PostMapping("/bymatchid")
    public List<GameRecordDTO> gameRecordByMatchId(@RequestBody GameRecordRequest gameRecordRequest){
    return gameRecordService.gameRecordByMatchId(gameRecordRequest.getMatchId());
}


    @Operation(summary = "전적 불러오기",description = "롤닉과 태그로 전적 불러옴")
@PostMapping("/bygameid")
    public Page<SimpleGameDTO> gameRecordByGameId(@AuthenticationPrincipal CustomUser user,
                                                  Pageable pageable) {

        String gameName = user.getGameName();
        String tagLine = user.getTagLine();

        return gameRecordService.gameRecordByGameId(
                gameName,tagLine,
                pageable);

}


}

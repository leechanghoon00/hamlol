package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;
import org.example.hamlol.service.SaveGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Tag(name = "Game API", description = "게임저장API")

@Controller
@RequestMapping("/api/game")
public class SaveGameController {
    private final SaveGameService saveGameService;

    public SaveGameController(SaveGameService saveGameService) {
        this.saveGameService = saveGameService;
    }

    @Operation(summary = "게임 저장", description = "게임저장")

    @PostMapping("/save")
    public String saveGame(@RequestBody GameSaveRequest request) {
        saveGameService.saveGame(request.matchDTO(), request.teamDTOs(), request.playerDTOs());
        return "Game saved !";
    }


    public static record GameSaveRequest(
            MatchDTO matchDTO,
            List<TeamDTO> teamDTOs,
            List<PlayerDTO> playerDTOs
    ) {}
}

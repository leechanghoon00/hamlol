package org.example.hamlol.service;

import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;

import java.util.List;

public interface SaveGameService {
    void saveGame(MatchDTO matchDTO, List<TeamDTO> teamDTOs, List<PlayerDTO> playerDTOs);

}

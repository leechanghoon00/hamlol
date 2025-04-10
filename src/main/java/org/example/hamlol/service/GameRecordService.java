package org.example.hamlol.service;


import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;

public interface GameRecordService {

    void watchGame(MatchDTO matchDTO, PlayerDTO playerDTO, TeamDTO teamDTO);


}

package org.example.hamlol.service.impl;

import org.example.hamlol.dto.*;
import org.example.hamlol.repository.GameRecordRepository;
import org.example.hamlol.service.GameRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRecordServieImpl implements GameRecordService {

    private final GameRecordRepository gameRecordRepository;

    public GameRecordServieImpl(GameRecordRepository gameRecordRepository) {
        this.gameRecordRepository = gameRecordRepository;
    }

    @Override
    public void watchGame(MatchDTO matchDTO, PlayerDTO playerDTO, TeamDTO teamDTO) {

    @Override
    public List<SimpleGameDTO> gameRecordByGameId(String riotIdGameName, String riotidtagline) {
        return  gameRecordRepository.findGameByGameId(riotIdGameName, riotidtagline);
    }
}

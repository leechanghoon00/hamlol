package org.example.hamlol.service;

import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.SimpleGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameRecordService {

List<GameRecordDTO> gameRecordByMatchId(String matchId);
Page<SimpleGameDTO> gameRecordByGameId(String riotIdGameName, String riotIdTagLine, Pageable pageable);

}

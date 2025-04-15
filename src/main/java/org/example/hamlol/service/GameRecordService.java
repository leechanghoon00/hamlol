package org.example.hamlol.service;

import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.SimpleGameDTO;

import java.util.List;

public interface GameRecordService {

List<GameRecordDTO> gameRecordByMatchId(String matchId);
List<SimpleGameDTO> gameRecordByGameId(String riotIdGameName, String riotIdTagLine);

}

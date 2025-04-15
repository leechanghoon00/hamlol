package org.example.hamlol.repository;


import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.SimpleGameDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRecordRepository{
    List<GameRecordDTO> findGameByMatchId(String matchId);
    List<SimpleGameDTO>  findGameByGameId(String riot_id_game_name , String riot_id_tagline);

}

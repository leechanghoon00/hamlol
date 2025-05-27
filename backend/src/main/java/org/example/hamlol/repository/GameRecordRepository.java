package org.example.hamlol.repository;


import org.example.hamlol.dto.GameRecordDTO;
import org.example.hamlol.dto.SimpleGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRecordRepository {
    List<GameRecordDTO> findGameByMatchId(String matchId);
Page<SimpleGameDTO> findGameByGameId(String riot_id_game_name, String riot_id_tagline, Pageable pageable);

}

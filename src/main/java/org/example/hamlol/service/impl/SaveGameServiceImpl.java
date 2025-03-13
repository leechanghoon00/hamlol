package org.example.hamlol.service.impl;

import jakarta.transaction.Transactional;
import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;
import org.example.hamlol.entity.MatchEntity;
import org.example.hamlol.entity.PlayerEntity;
import org.example.hamlol.entity.TeamEntity;
import org.example.hamlol.repository.MatchRepository;
import org.example.hamlol.repository.PlayerRepository;
import org.example.hamlol.repository.TeamRepository;
import org.example.hamlol.service.SaveGameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveGameServiceImpl implements SaveGameService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public SaveGameServiceImpl(MatchRepository matchRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public void saveGame(MatchDTO matchDTO, List<TeamDTO> teamDTOs, List<PlayerDTO> playerDTOs) {
        MatchEntity matchEntity = new MatchEntity(
                matchDTO.matchId(),
                matchDTO.gameDuration(),
                matchDTO.gamemode()
        );
        matchRepository.save(matchEntity);
        for (TeamDTO teamDTO : teamDTOs) {
            TeamEntity teamEntity = new TeamEntity(
                    teamDTO.matchId(),
                    teamDTO.teamId(),
                    teamDTO.win(),
                    teamDTO.bans(),
                    teamDTO.baronKills(),
                    teamDTO.championKills(),
                    teamDTO.dragonKills(),
                    teamDTO.hordeKills(),
                    teamDTO.inhibitorKills(),
                    teamDTO.riftHeraldKills(),
                    teamDTO.towerKills(),
                    matchEntity
            );
            teamRepository.save(teamEntity);
        }
        for (PlayerDTO playerDTO : playerDTOs) {
            PlayerEntity playerEntity = new PlayerEntity(
                    playerDTO.matchId(),
                    playerDTO.riotIdGameName(),
                    playerDTO.teamId(),
                    playerDTO.championId(),
                    playerDTO.damageDealtToBuildings(),
                    playerDTO.goldEarned(),
                    playerDTO.individualPosition(),
                    playerDTO.item0(),
                    playerDTO.item1(),
                    playerDTO.item2(),
                    playerDTO.item3(),
                    playerDTO.item4(),
                    playerDTO.item5(),
                    playerDTO.item6(),
                    playerDTO.kills(),
                    playerDTO.deaths(),
                    playerDTO.assists(),
                    playerDTO.kda(),
                    playerDTO.riotIdTagline(),
                    playerDTO.summoner1Id(),
                    playerDTO.summoner2Id(),
                    playerDTO.teamPosition(),
                    playerDTO.totalDamageDealtToChampions(),
                    playerDTO.totalDamageTaken(),
                    playerDTO.totalHealsOnTeammates(),
                    playerDTO.totalMinionsKilled(),
                    playerDTO.visionScore(),
                    playerDTO.visionWardsBoughtInGame(),
                    playerDTO.wardsPlaced(),
                    playerDTO.wardsKilled(),
                    playerDTO.win(),
                    matchEntity
            );
            playerRepository.save(playerEntity);
    }
}
}

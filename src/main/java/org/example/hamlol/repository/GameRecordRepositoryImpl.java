package org.example.hamlol.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.hamlol.dto.GameRecordDTO;
import static org.example.hamlol.entity.QMatchEntity.matchEntity;
import static org.example.hamlol.entity.QPlayerEntity.playerEntity;
import static org.example.hamlol.entity.QTeamEntity.teamEntity;

import org.example.hamlol.dto.SimpleGameDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public class GameRecordRepositoryImpl implements GameRecordRepository{
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public GameRecordRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
@Override
public List<SimpleGameDTO> findGameByGameId(String riot_id_game_name, String riot_id_tagline){

    return jpaQueryFactory
            .select(Projections.constructor(
                    SimpleGameDTO.class,
                    // Match
                    matchEntity.gameDuration,
                    matchEntity.gamemode,
                    matchEntity.gameCreation,
                    // Player
                    playerEntity.teamPosition,
                    playerEntity.win,
                    playerEntity.championName,
                    playerEntity.summoner1Id,
                    playerEntity.summoner2Id,
                    playerEntity.kills,
                    playerEntity.deaths,
                    playerEntity.assists,
                    playerEntity.kda,
                    playerEntity.item0,
                    playerEntity.item1,
                    playerEntity.item2,
                    playerEntity.item3,
                    playerEntity.item4,
                    playerEntity.item5,
                    playerEntity.item6,
                    playerEntity.totalMinionsKilled,
                    playerEntity.riotIdGameName,
                    playerEntity.riotIdTagline
            ))
            .from(matchEntity)
            .join(playerEntity).on(matchEntity.matchId.eq(playerEntity.matchId))
            .where(
                    playerEntity.riotIdGameName.eq(riot_id_game_name)
                            .and(playerEntity.riotIdTagline.eq(riot_id_tagline))
            )
            .fetch();
}


    @Override
    public List<GameRecordDTO> findGameByMatchId(String matchId) {


        return jpaQueryFactory.select(Projections.constructor(
                        GameRecordDTO.class,
                        // Match
                        matchEntity.gameDuration,
                        matchEntity.gamemode,
                        matchEntity.gameCreation,
                        // Player
                        playerEntity.teamType,
                        playerEntity.riotIdGameName,
                        playerEntity.championName,
                        playerEntity.damageDealtToBuildings,
                        playerEntity.goldEarned,
                        playerEntity.item0,
                        playerEntity.item1,
                        playerEntity.item2,
                        playerEntity.item3,
                        playerEntity.item4,
                        playerEntity.item5,
                        playerEntity.item6,
                        playerEntity.kills,
                        playerEntity.deaths,
                        playerEntity.assists,
                        playerEntity.kda,
                        playerEntity.riotIdTagline,
                        playerEntity.summoner1Id,
                        playerEntity.summoner2Id,
                        playerEntity.teamPosition,
                        playerEntity.totalDamageDealtToChampions,
                        playerEntity.totalDamageTaken,
                        playerEntity.totalHealsOnTeammates,
                        playerEntity.totalMinionsKilled,
                        playerEntity.visionScore,
                        playerEntity.visionWardsBoughtInGame,
                        playerEntity.wardsPlaced,
                        playerEntity.wardsKilled,
                        playerEntity.win,
                        playerEntity.primaryStyle1,
                        playerEntity.primaryStyle2,
                        playerEntity.primaryStyle3,
                        playerEntity.primaryStyle4,
                        playerEntity.subStyle1,
                        playerEntity.subStyle2,
                        // Team
                        teamEntity.bans,
                        teamEntity.baronKills,
                        teamEntity.championKills,
                        teamEntity.dragonKills,
                        teamEntity.hordeKills,
                        teamEntity.inhibitorKills,
                        teamEntity.riftHeraldKills,
                        teamEntity.towerKills
                ))
                .from(matchEntity)
                .join(playerEntity).on(matchEntity.matchId.eq(playerEntity.matchId))
                .join(teamEntity).on(matchEntity.matchId.eq(teamEntity.matchId).and(playerEntity.win.eq(teamEntity.win)))
                .where(matchEntity.matchId.eq(matchId))
                .fetch();
    }
}

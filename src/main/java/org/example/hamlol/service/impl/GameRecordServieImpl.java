package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.hamlol.dto.*;
import org.example.hamlol.repository.ChampRepository;
import org.example.hamlol.repository.GameRecordRepository;
import org.example.hamlol.service.ChampService;
import org.example.hamlol.service.GameRecordService;
import org.example.hamlol.urlenum.RiotUrlApi;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameRecordServieImpl implements GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    private final ChampService champService;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    public GameRecordServieImpl(GameRecordRepository gameRecordRepository, ChampService champService, ObjectMapper mapper, RestTemplate restTemplate) {
        this.gameRecordRepository = gameRecordRepository;
        this.champService = champService;
        this.mapper = mapper;
        this.restTemplate = restTemplate;

    }

    @Override
    public List<GameRecordDTO> gameRecordByMatchId(String matchId) {

        List<GameRecordDTO> record = gameRecordRepository.findGameByMatchId(matchId);
        List<ChampDTO> champ = champService.getChamp();

List<GameRecordDTO> newList= new ArrayList<>();

        for (GameRecordDTO r : record) {
            String newBan = r.bans(); 

            try {
                JsonNode arr = mapper.readTree(r.bans());  
                if (arr.isArray()) {
                    for (JsonNode banNode : arr) {
                        String rawId = banNode.get("championId").asText();
                        for (ChampDTO c : champ) {
                            if (c.key().equals(rawId)) {
                                ((ObjectNode) banNode).put("championId", c.id());
                                break;
                            }
                        }
                    }
                }
                newBan = arr.toString(); 
            } catch (IOException e) {
            System.out.println("실패");
            }
    GameRecordDTO updated = new GameRecordDTO(
            r.matchId(),
            r.gameDuration(),
            r.gamemode(),
            r.gameCreation(),
            r.teamType(),
            r.riotIdGameName(),
            r.championName(),
            r.damageDealtToBuildings(),
            r.goldEarned(),
            r.item0(),
            r.item1(),
            r.item2(),
            r.item3(),
            r.item4(),
            r.item5(),
            r.item6(),
            r.kills(),
            r.deaths(),
            r.assists(),
            r.kda(),
            r.riotIdTagline(),
            r.summoner1Id(),
            r.summoner2Id(),
            r.teamPosition(),
            r.totalDamageDealtToChampions(),
            r.totalDamageTaken(),
            r.totalHealsOnTeammates(),
            r.totalMinionsKilled(),
            r.visionScore(),
            r.visionWardsBoughtInGame(),
            r.wardsPlaced(),
            r.wardsKilled(),
            r.win(),
            r.primaryStyle1(),
            r.primaryStyle2(),
            r.primaryStyle3(),
            r.primaryStyle4(),
            r.subStyle1(),
            r.subStyle2(),
            newBan,
            r.baronKills(),
            r.championKills(),
            r.dragonKills(),
            r.hordeKills(),
            r.inhibitorKills(),
            r.riftHeraldKills(),
            r.towerKills()
    );
newList.add(updated);


}

        System.out.println(newList);

        return newList;

    }



    @Override
    public List<SimpleGameDTO> gameRecordByGameId(String riotIdGameName, String riotidtagline) {
        return  gameRecordRepository.findGameByGameId(riotIdGameName, riotidtagline);
    }
}

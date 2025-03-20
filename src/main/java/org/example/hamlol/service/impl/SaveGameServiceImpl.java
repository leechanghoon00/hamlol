package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.example.hamlol.service.ApiKeyProvider;
import org.example.hamlol.service.SaveGameService;
import org.example.hamlol.urlenum.RiotUrlApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service  // 이 클래스가 Spring의 서비스 컴포넌트임을 선언
public class SaveGameServiceImpl implements SaveGameService {

    @Autowired
    private RestTemplate restTemplate;

    // API 키를 설정파일이나 다른 소스에서 가져옵니다.
    private final String apiKey = ApiKeyProvider.getApiKey();

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    // Riot API URL 템플릿. 예: "https://asia.api.riotgames.com/lol/match/v5/matches/{matchId}"
    private static final String RIOT_API_URL = RiotUrlApi.MATCH.getUrl();

    /**
     * 클라이언트로부터 전달받은 JSON 문자열(예: {"matchId": "KR_7565284912"})을
     * MatchDTO 객체로 매핑하고 saveGame()을 호출하는 헬퍼 메서드.
     */
    public void saveGameFromJson(String matchJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열을 MatchDTO 객체로 매핑
            MatchDTO matchDTO = objectMapper.readValue(matchJson, MatchDTO.class);
            // 테스트용으로 팀, 플레이어 정보는 빈 리스트로 전달합니다.
            saveGame(matchDTO, Collections.emptyList(), Collections.emptyList());
        } catch (Exception e) {
            System.err.println("입력 JSON 파싱 에러: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * SaveGameService 인터페이스 구현 메서드.
     * 전달받은 MatchDTO(주로 matchId)와 팀, 플레이어 정보(빈 리스트)를 이용하여
     * Riot API에서 매치 데이터를 조회하고, 응답 JSON을 파싱하여 DTO로 변환한 후,
     * 엔티티로 변환해서 DB에 저장합니다.
     */
    @Override
    @Transactional  // 트랜잭션 범위 내에서 실행하여 데이터 일관성을 보장
    public void saveGame(MatchDTO matchDTO, List<TeamDTO> teamDTOs, List<PlayerDTO> playerDTOs) {
        // 1. 전달받은 MatchDTO에서 matchId 추출
        String matchId = matchDTO.matchId();

        // 2. UriComponentsBuilder를 사용해 URL 템플릿에서 {matchId} 치환 및 API 키 쿼리파라미터 추가
        String url = UriComponentsBuilder
                .fromHttpUrl(RIOT_API_URL)
                .queryParam("api_key", apiKey)
                .buildAndExpand(matchId)
                .toUriString();

        System.out.println("Constructed URL: " + url);

        // 3. RestTemplate으로 Riot API에 GET 요청을 보내고 응답 문자열을 받음
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("API Response: " + response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 응답 문자열을 JsonNode 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);

            // ** MatchDTO 추출 **
            JsonNode metadata = root.get("metadata");
            JsonNode info = root.get("info");
            String extractedMatchId = metadata.get("matchId").asText();
            int gameDuration = info.get("gameDuration").asInt();
            String gamemode = info.get("gameMode").asText();
            // "gameCreation" 필드는 값이 클 수 있으므로 long으로 처리하고, 없으면 0L로 처리
            long gameCreation = info.has("gameCreation") ? info.get("gameCreation").asLong() : 0L;

            // MatchDTO는 이제 gameCreation이 long 타입이어야 합니다.
            MatchDTO extractedMatchDto = new MatchDTO(extractedMatchId, (long) gameDuration, gamemode, gameCreation);
            System.out.println("Extracted MatchDTO: " + extractedMatchDto);

            // ** PlayerDTO 추출 **
            List<PlayerDTO> extractedPlayerList = new ArrayList<>();
            JsonNode participantsNode = info.get("participants");
            if (participantsNode != null && participantsNode.isArray()) {
                for (JsonNode participant : participantsNode) {
                    String riotIdGameName = participant.get("riotIdGameName").asText("");
                    String championId = participant.get("championId").asText();
                    int damageDealtToBuildings = participant.get("damageDealtToBuildings").asInt(0);
                    int goldEarned = participant.get("goldEarned").asInt(0);
                    String individualPosition = participant.get("individualPosition").asText("");
                    String item0 = participant.get("item0").asText("");
                    String item1 = participant.get("item1").asText("");
                    String item2 = participant.get("item2").asText("");
                    String item3 = participant.get("item3").asText("");
                    String item4 = participant.get("item4").asText("");
                    String item5 = participant.get("item5").asText("");
                    String item6 = participant.get("item6").asText("");
                    int kills = participant.get("kills").asInt(0);
                    int deaths = participant.get("deaths").asInt(0);
                    int assists = participant.get("assists").asInt(0);
                    //단순하게 kda로 접근하면 자꾸 null값이 반환되서 challenges노드를 가져온후 그안에서 kda값 추춘ㄹ해봄
                    JsonNode challengesNode = participant.get("challenges");
                    float kda = (float) ((challengesNode != null && challengesNode.get("kda") != null)
                            ? challengesNode.get("kda").asDouble(0.0)
                            : 0.0);

                    String riotIdTagline = participant.get("riotIdTagline").asText("");
                    String summoner1Id = participant.get("summoner1Id").asText("");
                    String summoner2Id = participant.get("summoner2Id").asText("");
                    String teamPosition = participant.get("teamPosition").asText("");
                    int totalDamageDealtToChampions = participant.get("totalDamageDealtToChampions").asInt(0);
                    int totalDamageTaken = participant.get("totalDamageTaken").asInt(0);
                    int totalHealsOnTeammates = participant.get("totalHealsOnTeammates").asInt(0);
                    int totalMinionsKilled = participant.get("totalMinionsKilled").asInt(0);
                    int visionScore = participant.get("visionScore").asInt(0);
                    int visionWardsBoughtInGame = participant.get("visionWardsBoughtInGame").asInt(0);
                    int wardsPlaced = participant.get("wardsPlaced").asInt(0);
                    int wardsKilled = participant.get("wardsKilled").asInt(0);
                    boolean win = participant.get("win").asBoolean(false);

                    // 팀 타입 결정: teamId가 100이면 "blue", 200이면 "red"
                    int teamId = participant.get("teamId").asInt(100);
                    String teamType = (teamId == 100) ? "blue" : "red";

                    PlayerDTO playerDto = new PlayerDTO(
                            extractedMatchId,
                            teamType,
                            riotIdGameName,
                            championId,
                            damageDealtToBuildings,
                            goldEarned,
                            individualPosition,
                            item0, item1, item2, item3, item4, item5, item6,
                            kills,
                            deaths,
                            assists,
                            kda,
                            riotIdTagline,
                            summoner1Id,
                            summoner2Id,
                            teamPosition,
                            totalDamageDealtToChampions,
                            totalDamageTaken,
                            totalHealsOnTeammates,
                            totalMinionsKilled,
                            visionScore,
                            visionWardsBoughtInGame,
                            wardsPlaced,
                            wardsKilled,
                            win
                    );
                    extractedPlayerList.add(playerDto);
                }
            } else {
                System.err.println("Participants node가 존재하지 않거나 배열이 아닙니다.");
            }
            System.out.println("Extracted PlayerDTOs: " + extractedPlayerList);

            // ** TeamDTO 추출 **
            List<TeamDTO> extractedTeamList = new ArrayList<>();
            JsonNode teamsNode = info.get("teams");
            if (teamsNode != null && teamsNode.isArray()) {
                for (JsonNode team : teamsNode) {
                    int tId = team.get("teamId").asInt();
                    // teamId 값에 따라 blue(100) 또는 red(200)로 결정
                    String teamType = (tId == 100) ? "blue" : "red";
                    boolean teamWin = team.get("win").asBoolean(false);
                    // bans 배열을 JSON 문자열로 저장
                    String bans = team.get("bans").toString();

                    JsonNode objectives = team.get("objectives");
                    int baronKills = objectives.get("baron").get("kills").asInt(0);
                    int championKills = objectives.get("champion").get("kills").asInt(0);
                    int dragonKills = objectives.get("dragon").get("kills").asInt(0);
                    int hordeKills = objectives.has("horde") ? objectives.get("horde").get("kills").asInt(0) : 0;
                    int inhibitorKills = objectives.get("inhibitor").get("kills").asInt(0);
                    int riftHeraldKills = objectives.has("riftHerald") ? objectives.get("riftHerald").get("kills").asInt(0) : 0;
                    int towerKills = objectives.get("tower").get("kills").asInt(0);

                    TeamDTO teamDto = new TeamDTO(
                            extractedMatchId,
                            teamType,
                            tId,
                            teamWin,
                            bans,
                            baronKills,
                            championKills,
                            dragonKills,
                            hordeKills,
                            inhibitorKills,
                            riftHeraldKills,
                            towerKills
                    );
                    extractedTeamList.add(teamDto);
                }
            } else {
                System.err.println("Teams node가 존재하지 않거나 배열이 아닙니다.");
            }
            System.out.println("Extracted TeamDTOs: " + extractedTeamList);

            // --- 엔티티로 변환 후 DB에 저장 ---

            // 1. MatchEntity 변환 및 저장
            MatchEntity matchEntity = new MatchEntity(
                    extractedMatchDto.matchId(),
                    extractedMatchDto.gameDuration(),
                    extractedMatchDto.gamemode(),
                    extractedMatchDto.gameCreation()  // 이제 gameCreation은 long 타입이어야 함
            );
            matchRepository.save(matchEntity);
            System.out.println("Saved MatchEntity: " + matchEntity);

            // 2. TeamEntity 변환 및 저장
            // TeamEntity의 생성자는 (matchId, teamType, win, bans, baronKills, championKills, dragonKills, hordeKills, inhibitorKills, riftHeraldKills, towerKills, MatchEntity) 를 받습니다.
            List<TeamEntity> teamEntities = new ArrayList<>();
            for (TeamDTO teamDTO : extractedTeamList) {
                TeamEntity teamEntity = new TeamEntity(
                        teamDTO.matchId(),
                        teamDTO.teamType(),   // 이미 "blue" 또는 "red"로 결정됨
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
                teamEntities.add(teamEntity);
            }
            teamRepository.saveAll(teamEntities);
            System.out.println("Saved TeamEntities: " + teamEntities);

            // 3. PlayerEntity 변환 및 저장
            List<PlayerEntity> playerEntities = new ArrayList<>();
            for (PlayerDTO playerDTO : extractedPlayerList) {
                PlayerEntity playerEntity = new PlayerEntity(
                        playerDTO.matchId(),
                        playerDTO.teamType(),
                        playerDTO.riotIdGameName(),
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
                        matchEntity   // 연관된 MatchEntity 설정
                );
                playerEntities.add(playerEntity);
            }
            playerRepository.saveAll(playerEntities);
            System.out.println("Saved PlayerEntities: " + playerEntities);
        } catch (Exception e) {
            System.err.println("API 응답 파싱 에러: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

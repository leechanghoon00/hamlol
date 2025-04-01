package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.hamlol.dto.MatchDTO;
import org.example.hamlol.dto.PlayerDTO;
import org.example.hamlol.dto.TeamDTO;
import org.example.hamlol.entity.*;
import org.example.hamlol.repository.*;
import org.example.hamlol.service.ApiKeyProvider;
import org.example.hamlol.service.SaveGameService;
import org.example.hamlol.urlenum.RiotUrlApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;


    // 라이엇 api 주소 ENum으로 관리
    private static final String RIOT_API_URL = RiotUrlApi.MATCH.getUrl();


    // json 문자열을 받아서 matchdto로 파싱후 저장 처리
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


    @Override
    @Transactional
    public void saveGame(MatchDTO matchDTO, List<TeamDTO> teamDTOs, List<PlayerDTO> playerDTOs) {

        // 1. 로그인한 사용자 정보 조회
        // 시큐리티에서 현재 로그인된 사용자의 인증 젖ㅇ보를 가져옴
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName(); //jwt에서 userEmail을 getname으로 꺼냄
        //  db에서  email로 사용자정보 조회함
        UserEntity loginUser = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("로그인한 사용자정보를 찾을수 없음"));

        // 2. 사용자와 연동된 롤 계정 조회
        //로그인한 사용자와 연결된 Account entity 조회
        List<AccountEntity> linkedAccounts = accountRepository.findByUserEntity(loginUser);
        // 계정목록에서 gamename만 뺴서 리스트로 변환  .stream = entity를 하나씩 순회할수 있게 해줌, .map() = entity에서 롤닉만 호출
        List<String>myGamenames = linkedAccounts.stream().map(AccountEntity::getGameName)
                .toList();
        System.out.println("로그인한 사용자의 롤 닉: " + myGamenames);


        // 3. 저장하려는 match id 
        String matchId = matchDTO.matchId();

        // 4. UriComponentsBuilder를 사용해 URL 템플릿에서 {matchId} 치환 및 API 키 쿼리파라미터 추가해서 api키 완성시키기
        String url = UriComponentsBuilder.fromHttpUrl(RIOT_API_URL)
                .queryParam("api_key", apiKey)
                .buildAndExpand(matchId)
                .toUriString();

        System.out.println("완성된 URL: " + url);

        // 5. RestTemplate으로 Riot API에 GET 요청을 보내고 응답 문자열을 받음
        String response = restTemplate.getForObject(url, String.class);

        System.out.println("응답 받은 API: " + response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 응답 문자열을 JsonNode 트리 구조로 변환
            JsonNode root = objectMapper.readTree(response);
            JsonNode info = root.get("info");
            JsonNode metadata = root.get("metadata");

            // 전적 기본 정보 추출하기
            String extractedMatchId = metadata.get("matchId").asText();
            int gameDuration = info.get("gameDuration").asInt();
            String gamemode = info.get("gameMode").asText();
            // "gameCreation" 필드는 값이 클 수 있으므로 long으로 처리하고, 없으면 0L로 처리
            long gameCreation = info.has("gameCreation") ? info.get("gameCreation").asLong() : 0L;


            // 새로운 match dto 생성
            MatchDTO extractedMatchDto = new MatchDTO(extractedMatchId, (long) gameDuration, gamemode, gameCreation);
            System.out.println("추출된 MatchDTO: " + extractedMatchDto);

            // 참가자 정보 추출 + 사용자와 연동된 게임이름 포함여부 확인
            List<PlayerDTO> extractedPlayerList = new ArrayList<>();
            JsonNode participantsNode = info.get("participants");
            boolean isMatchFound = false
                    ;

            // 참가자 목록을 돌리면서 로그인한 유저의 게임아이디가있는지 확인
            if (participantsNode != null && participantsNode.isArray()) {
                for (JsonNode participant : participantsNode) {
                    String riotIdGameName = participant.get("riotIdGameName").asText("");

                    // 닉이 일치하면 저장 조건넣기
                    if (myGamenames.contains(riotIdGameName)) {
                        isMatchFound = true;
                    }
// 플레이어디티오 변환ㄴ
                    PlayerDTO playerDto = new PlayerDTO(
                            extractedMatchId,
                            (participant.get("teamId").asInt(100) == 100) ? "blue" : "red",
                            riotIdGameName,
                            participant.get("championId").asText(),
                            participant.get("damageDealtToBuildings").asInt(0),
                            participant.get("goldEarned").asInt(0),
                            participant.get("individualPosition").asText("")
                            , participant.get("item0").asText(""), participant.get("item1").asText(""),
                            participant.get("item2").asText(""), participant.get("item3").asText(""),
                            participant.get("item4").asText(""), participant.get("item5").asText(""),
                            participant.get("item6").asText(""),
                            participant.get("kills").asInt(0),
                            participant.get("deaths").asInt(0),
                            participant.get("assists").asInt(0),
                            (float) ((participant.has("challenges") && participant.get("challenges").get("kda") != null)
                                    ? participant.get("challenges").get("kda").asDouble(0.0) : 0.0),
                            participant.get("riotIdTagline").asText(""),
                            participant.get("summoner1Id").asText(""),
                            participant.get("summoner2Id").asText(""),
                            participant.get("teamPosition").asText(""),
                            participant.get("totalDamageDealtToChampions").asInt(0),
                            participant.get("totalDamageTaken").asInt(0),
                            participant.get("totalHealsOnTeammates").asInt(0),
                            participant.get("totalMinionsKilled").asInt(0),
                            participant.get("visionScore").asInt(0),
                            participant.get("visionWardsBoughtInGame").asInt(0),
                            participant.get("wardsPlaced").asInt(0),
                            participant.get("wardsKilled").asInt(0),
                            participant.get("win").asBoolean(false)
                    );
                    extractedPlayerList.add(playerDto);
                }
            }
            // 본인전적아니면 오류메세지
            if (!isMatchFound) {
                System.out.println("본인의 전적이 아닌경우 등록이 불가합니다.");
                throw new IllegalArgumentException("본인의 전적이 아니므로 저장할 수 없습니다.");
            }

            // 팀 정보 DTO로 추출
            List<TeamDTO> extractedTeamList = new ArrayList<>();
            JsonNode teamsNode = info.get("teams");
            if (teamsNode != null && teamsNode.isArray()) {
                for (JsonNode team : teamsNode) {
                    String teamType = team.get("teamId").asInt() == 100 ? "blue" : "red";
                    TeamDTO teamDto = new TeamDTO(
                            extractedMatchId,
                            teamType,
                            team.get("teamId").asInt(),
                            team.get("win").asBoolean(false),
                            team.get("bans").toString(),
                            team.get("objectives").get("baron").get("kills").asInt(0),
                            team.get("objectives").get("champion").get("kills").asInt(0),
                            team.get("objectives").get("dragon").get("kills").asInt(0),
                            team.get("objectives").has("horde") ? team.get("objectives").get("horde").get("kills").asInt(0) : 0,
                            team.get("objectives").get("inhibitor").get("kills").asInt(0),
                            team.get("objectives").has("riftHerald") ? team.get("objectives").get("riftHerald").get("kills").asInt(0) : 0,
                            team.get("objectives").get("tower").get("kills").asInt(0)
                    );
                    extractedTeamList.add(teamDto);
                }
            }

            //  DB 저장 처리(Match entity를 직접 생성후 저장)
            MatchEntity matchEntity = new MatchEntity(
                    extractedMatchDto.matchId(),
                    extractedMatchDto.gameDuration(),
                    extractedMatchDto.gamemode(),
                    extractedMatchDto.gameCreation()
            );
            matchRepository.save(matchEntity);
            //  DB 저장 처리(Team map으로 리스트생성후 한번에 저장)
            List<TeamEntity> teamEntities = extractedTeamList.stream().map(dto ->
                    new TeamEntity(
                            dto.matchId(), dto.teamType(), dto.win(), dto.bans(),
                            dto.baronKills(), dto.championKills(), dto.dragonKills(), dto.hordeKills(),
                            dto.inhibitorKills(), dto.riftHeraldKills(), dto.towerKills(),
                            matchEntity
                    )
            ).toList();
            teamRepository.saveAll(teamEntities);
//  DB 저장 처리(Player map으로 리스트생성후 한번에 저장)
            List<PlayerEntity> playerEntities = extractedPlayerList.stream().map(dto ->
                    new PlayerEntity(
                            dto.matchId(), dto.teamType(), dto.riotIdGameName(), dto.championId(),
                            dto.damageDealtToBuildings(), dto.goldEarned(), dto.individualPosition(),
                            dto.item0(), dto.item1(), dto.item2(), dto.item3(), dto.item4(), dto.item5(), dto.item6(),
                            dto.kills(), dto.deaths(), dto.assists(), dto.kda(), dto.riotIdTagline(),
                            dto.summoner1Id(), dto.summoner2Id(), dto.teamPosition(), dto.totalDamageDealtToChampions(),
                            dto.totalDamageTaken(), dto.totalHealsOnTeammates(), dto.totalMinionsKilled(),
                            dto.visionScore(), dto.visionWardsBoughtInGame(), dto.wardsPlaced(), dto.wardsKilled(),
                            dto.win(), matchEntity
                    )
            ).toList();
            playerRepository.saveAll(playerEntities);



            System.out.println(" 경기 저장 완료");

        } catch (Exception e) {

            System.err.println(" Riot API 응답 파싱 에러: " + e.getMessage());
            // 클라이언트한테 반환되는 에러 메시지
            throw new IllegalArgumentException("본인의 전적이 아닌경우 등록이 불가합니다.");


        }
    }
}
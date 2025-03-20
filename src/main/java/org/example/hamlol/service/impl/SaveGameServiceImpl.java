package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Map;


@Service
public class SaveGameServiceImpl implements SaveGameService {


    @Autowired
    private RestTemplate restTemplate;


    private final String apiKey = ApiKeyProvider.getApiKey();
    // 라이엇 api에 요청하는 값
//    private static final String RIOT_API_URL = /lol/match/v5/matches/{matchId}
    private  static final String RIOT_API_URL = RiotUrlApi.MATCH.getUrl();

    @Override
    @Transactional
    public void saveGame(MatchDTO matchDTO, List<TeamDTO> teamDTOs, List<PlayerDTO> playerDTOs) {
        // api키 가져오기
        String apiKey = ApiKeyProvider.getApiKey();
        String matchId = matchDTO.matchId();
        // UriComponentsBuilder를 사용하여 URL을 구성합니다.
        // buildAndExpand(matchId)를 호출하여 {matchId} 자리에 실제 값이 삽입됩니다.
        String url = UriComponentsBuilder
                .fromHttpUrl(RIOT_API_URL)
                .queryParam("api_key", apiKey)
                .buildAndExpand(matchId)
                .toUriString();

        // URL이 잘 완성되었는지 확인하기 위해 로그 출력 (Postman 테스트 시에도 동일 URL 사용)
        System.out.println("Constructed URL: " + url);
    }

}

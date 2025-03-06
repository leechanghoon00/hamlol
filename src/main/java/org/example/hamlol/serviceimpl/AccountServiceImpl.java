package org.example.hamlol.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hamlol.dto.AccountRequestDto;
import org.example.hamlol.dto.AccountResponseDTO;
import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.repository.AccountRepository;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.UUID;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired //레파지토리 자동 주입
    private AccountRepository accountRepository;
    @Autowired // 템플레이트 자동주입 HTTP요청을 보낼대 사용
    private RestTemplate restTemplate;

    private static final String RIOT_API_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}";

    public AccountServiceImpl(AccountRepository accountRepository, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplate;
    }



    // JSON 응답에서 puuid 값을 추출하는 메소드
    private String extractPuuidFromResponse(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();  // Jackson의 ObjectMapper 객체 생성
        JsonNode jsonNode = objectMapper.readTree(response);  // JSON 문자열을 트리 구조로 변환
        return jsonNode.get("puuid").asText();  // "puuid" 필드를 추출하여 반환
    }


    @Override
    public AccountResponseDTO getAccountInfoAndSaveAccount(AccountRequestDto accountRequestDto) throws Exception{

        String apiKey = "라이엇";  // Riot API 키

        // URI 빌더를 사용하여 Riot API 요청 URL 생성 (경로 변수 적용)
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.
                fromHttpUrl(RIOT_API_URL)
                .queryParam("api_key", apiKey);
        String uriString = uriBuilder.buildAndExpand(accountRequestDto.gameName(),accountRequestDto.tagLine()).toUriString();

        try {
            // GET 요청을 보내고 응답을 문자열로 받음
            String response = restTemplate.getForObject(uriString, String.class);

            // 응답 JSON에서 puuid 추출
            String puuid = extractPuuidFromResponse(response);
            if (accountRepository.existsById(accountRequestDto.gameName())) {
                throw new IllegalArgumentException("Account already exists");
            }

            // 새로운 puuid 생성 (클라이언트에서는 puuid가 없으므로 서버에서 생성)
            String generatedPuuid = UUID.randomUUID().toString();


            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setPuuid(generatedPuuid);
            accountEntity.setGameName(accountRequestDto.gameName());
            accountEntity.setTagLine(accountRequestDto.tagLine());


            accountRepository.save(accountEntity);
            System.out.println(("저장"));

            return new AccountResponseDTO(generatedPuuid, accountRequestDto.gameName(), accountRequestDto.tagLine());

        } catch (HttpClientErrorException.NotFound e) {
            throw new Exception("플레이어를 찾을 수 없습니다 (404 Not Found)", e);
        } catch (HttpClientErrorException.Conflict e) {
            throw new Exception("데이터 충돌 발생", e);
        }


    }


}

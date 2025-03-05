package org.example.hamlol.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hamlol.dto.AccountDto;
import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.repository.AccountRepository;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired //레파지토리 자동 주입
    private AccountRepository accountRepository;

    @Autowired // 템플레이트 자동주입 HTTP요청을 보낼대 사용
    private RestTemplate restTemplate;

    private static final String RIOT_API_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}";

    @Override
    public void saveAccount(AccountDto accountDto) {
        // db에 있는지 확인
        if (accountRepository.existsById(accountDto.gameName())) {
            throw new IllegalArgumentException("Account already exists");
        }

        // AccountDto를 AccountEntity로 변환하여 DB에 저장
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setGameName(accountDto.gameName());
        accountEntity.setTagLine(accountDto.tagLine());
        accountEntity.setPuuid(accountDto.puuid());

        accountRepository.save(accountEntity);
        System.out.println("저장");
    }

    @Override
    public AccountDto AccountInfo(String gameName, String tagLine) throws Exception {

        String apiKey = "라이엇 키";  // Riot API 키
        String url = RIOT_API_URL.replace("{gameName}", gameName).replace("{tagLine}", tagLine)+("?api_key="+apiKey); // URL에 gameName과 tagLine 삽입

        try {
            // URL에 API 키를 쿼리 파라미터로 추가
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("api_key", apiKey);  // API 키를 쿼리 파라미터로 추가
            String testUrl = uriBuilder.toUriString();

            // GET 요청을 보내고 응답을 문자열로 받음
            String response = restTemplate.getForObject(url, String.class);

            // 응답에서 필요한 정보 추출
            String puuid = extractPuuidFromResponse(response);  // 응답에서 puuid 추출

            // AccountDto 객체 생성 후 반환
            return new AccountDto(puuid, gameName, tagLine);
        } catch (Exception e) {
            throw new Exception("Failed to fetch account info from Riot API", e);  // 예외 처리
        }
    }

    // JSON 응답에서 puuid 값을 추출하는 메소드
    private String extractPuuidFromResponse(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();  // Jackson의 ObjectMapper 객체 생성
        JsonNode jsonNode = objectMapper.readTree(response);  // JSON 문자열을 트리 구조로 변환
        return jsonNode.get("puuid").asText();  // "puuid" 필드를 추출하여 반환
    }


}

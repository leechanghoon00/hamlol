package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hamlol.dto.AccountRequestDTO;
import org.example.hamlol.dto.AccountRequestDTO;
import org.example.hamlol.dto.AccountResponseDTO;
import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.entity.UserEntity;
import org.example.hamlol.repository.AccountRepository;
import org.example.hamlol.repository.ApiKeyRepository;
import org.example.hamlol.repository.UserRepository;
import org.example.hamlol.service.AccountService;
import org.example.hamlol.service.ApiKeyProvider;
import org.example.hamlol.urlenum.RiotUrlApi;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;// HTTP 요청을 외부 API에 보낼떄 사용하는 객체
    @Autowired
    private final ApiKeyProvider apiKeyProvider;


    // 라이엇 api에 요청하는 값
//    private static final String RIOT_API_URL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}";
    private  static final String RIOT_API_URL = RiotUrlApi.FIND_BY_PUUID.getUrl();


    // 생성자 주입을 통해 명시적으로 의존성을 주입받음 (테스트와 유지보수에 유리)
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              RestTemplate restTemplate,
                              ApiKeyProvider apiKeyProvider) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.apiKeyProvider = apiKeyProvider;
    }

    @Override
    // AccountRequestDto를 받아 Riotapi로부터 계정정보 조회후 저장하고  AccountResponseDTO 형태로 반환
    public AccountResponseDTO getAccountInfoAndSaveAccount(AccountRequestDTO accountRequestDto) throws Exception {
// throws Exception : 메소드 실행 도중 발생할 수 있는 예외를 전달함

        // api키 가져오기
        String apiKey = apiKeyProvider.getApiKey();

        // URI 빌더를 사용하여 Riot API 요청 URL 생성 (경로 변수 적용)
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.
                fromHttpUrl(RIOT_API_URL)
                .queryParam("api_key", apiKey);


        String uriString = uriBuilder.buildAndExpand( //accountRequestDto에서 게임이름이랑 태그를 받아 URL 경로변수에 넣어 URL생성
                accountRequestDto.gameName(),
                accountRequestDto.tagLine()
        ).toUriString();

        try {
            // restTemplate를 이용해 지정된 URL로 HTTP GET요청을 보내고, 응답을 문자열 형태로 받음.
            String response = restTemplate.getForObject(uriString, String.class);

            // 응답 JSON에서 puuid 추출
            String puuid = extractPuuidFromResponse(response);
            //중복 방지
            if (accountRepository.existsById(accountRequestDto.gameName())) { //gameName이 데이터베이스ㅔ 있는지 확인
                throw new IllegalArgumentException("이미 있음");
            }

            // 새로운 puuid 생성 (클라이언트에서는 puuid가 없으므로 서버에서 생성)
            String generatedPuuid = UUID.randomUUID().toString();

            //accountRequestDto에서 가져온 UserName을 통해 userRepository에서 입력받은userName과 같은 이름을 UserEntity에 있는 user에 객체로 넣는다
            UserEntity user = userRepository.findByUserName(accountRequestDto.userName());

            // 새 계정정보를 저장할 인스턴스 생성후 set으로 각각 새로 집어넣음
            AccountEntity accountEntity = new AccountEntity(
                    generatedPuuid,
                    accountRequestDto.gameName(),
                    accountRequestDto.tagLine(),
                    user,
                    accountRequestDto.userName()
            );
            accountRepository.save(accountEntity);




            // 레파지토리의 JPA를 이용하여 AccountEntity 객체를 데이터 베이스에 저장 후 "저장" 출력
            accountRepository.save(accountEntity);
            System.out.println(("저장"));

            // 사용자에게 계정 생성 결과 전달
            return new AccountResponseDTO(generatedPuuid, accountRequestDto.gameName(), accountRequestDto.tagLine(),accountRequestDto.userName());

            // 예외처리
        } catch (HttpClientErrorException.NotFound e) {
            // Riot API 호출 결과 404 Not Found: 플레이어 정보를 찾을 수 없는 경우
            throw new Exception("플레이어를 찾을 수 없습니다 (404 Not Found)", e);
        } catch (HttpClientErrorException.Conflict e) {
            // Riot API 호출 결과 409 Conflict: 데이터 충돌 발생 시
            throw new Exception("데이터 충돌 발생", e);
        }


    }


    // JSON 응답에서 puuid 값을 추출하는 메소드
    //extractPuuidFromResponse(String response):JSON문자열을 받아서 처리함
    private String extractPuuidFromResponse(String response) throws Exception {
        // ObjectMapper = JSon데이터를 -> 자바 객체로 or Java 객체를 -> Json 객체로 변환할 때 사용함
        ObjectMapper objectMapper = new ObjectMapper();
        //전달된 JSON문자열을 JsonnNode(Json데이터를 트리 구조로 표현하는 Jackson의 객체)객체로 변환 = JsonNode라는 트리 형태의 객체로 저장됨
        JsonNode jsonNode = objectMapper.readTree(response);
        // JSON 트리에서 "puuid" 라는 키에 해당하는 값을 찾음
        // asText() = 찾은 값을 문자열로 반환함, 만약 존재하지않으면 null이 반환됨
        return jsonNode.get("puuid").asText();
    }

}

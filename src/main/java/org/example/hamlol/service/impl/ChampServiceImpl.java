package org.example.hamlol.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.hamlol.dto.ChampDTO;
import org.example.hamlol.entity.ChampEntity;
import org.example.hamlol.repository.ChampRepository;
import org.example.hamlol.service.ChampService;
import org.example.hamlol.urlenum.RiotUrlApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class ChampServiceImpl implements ChampService {
    @Autowired
    private final ChampRepository champRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper; // JSON 파싱용

    public ChampServiceImpl(ChampRepository champRepository) {
        this.champRepository = champRepository;
    }





    @Override
    @Transactional
    public void saveChamp() {
        String url = RiotUrlApi.FIND_BY_CHAMP.getUrl();
        // get요청보내고 STring형태로 받음
        String response = restTemplate.getForObject(url,String.class);

        try {
            // 받은 데이터를 Jsonnode로 파싱하고
            JsonNode root = objectMapper.readTree(response);
            // 파싱한 데이터 'data'로 저장
            JsonNode data = root.get("data");

            //빈 리스트DTO생성
            List<ChampDTO> DtoList = new ArrayList<>();
            Iterator<JsonNode> elements = data.elements();
            // 저장
            while (elements.hasNext()){
                JsonNode champ = elements.next();
                String id = champ.get("id").asText();
                String key = champ.get("key").asText();
                String name = champ.get("name").asText();

                DtoList.add(new ChampDTO(id,key,name));
            }
            //스트림으로 엔티티로 변환
            DtoList.stream()
                    .map(dto -> new ChampEntity(dto.id(), dto.key(), dto.name())) //dto에서 entity로 전달
                    .forEach(champRepository::save); //champRepository save를 이용해 저장
        }catch (Exception e){
            throw new RuntimeException("오류",e);

        }



    }
}

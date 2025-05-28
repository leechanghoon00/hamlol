package org.example.hamlol.service;

import org.example.hamlol.entity.ApiKeyEntity;
import org.example.hamlol.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider{


    private static ApiKeyRepository apiKeyRepository;

    @Autowired
    public ApiKeyProvider(ApiKeyRepository apiKeyRepository) {
        ApiKeyProvider.apiKeyRepository = apiKeyRepository;
    }

    // 내부에서 초기화
    private static class ApiKeyHolder {
        private static final String INSTANCE = apiKey();
    }
//db에서  pai 키 가져오기
    private static String apiKey() {
        ApiKeyEntity key = apiKeyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("api키가없음"));
        String apiKey = key.getApiKey();
        System.out.println("API키 추가완료");
        return apiKey;
    }

    //외부에서 이걸통해서만 접근하게함
    public static String getApiKey() {
        return ApiKeyHolder.INSTANCE;

    }
}

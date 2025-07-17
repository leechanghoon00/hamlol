package org.example.hamlol.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hamlol.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 단순 저장
    @Override
    @Transactional
    public void setValues(String key, String value) {
        //key에 value 영구 저장
        redisTemplate.opsForValue().set(key, value);
    }

    // 저장 + 유효시간 설정
    @Override
    @Transactional
    public void setValuesWithTimeout(String key, String value, long timeoutMillis) {
        //key에 value  저장 , 시간지나면 자동삭제
        redisTemplate.opsForValue().set(key, value, timeoutMillis, TimeUnit.MILLISECONDS);
    }

    // 값 조회
    @Override
    public String getValues(String key) {
        //key로 저장된 value 조회
        return redisTemplate.opsForValue().get(key);
    }

    // 값 삭제
    @Override
    @Transactional
    public void deleteValues(String key) {
        // 삭제
        redisTemplate.delete(key);
    }
}
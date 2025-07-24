package org.example.hamlol.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.hamlol.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * ✅ 단순 저장 (영구 저장)
     */
    @Override
    @Transactional
    public void setValues(String key, String value) {
        try {
            System.out.println("✅ Redis 저장 시도: " + key);
            redisTemplate.opsForValue().set(key, value);
            System.out.println("✅ Redis 저장 성공 (영구 저장): " + key);
        } catch (Exception e) {
            System.out.println("❌ Redis 저장 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ✅ 저장 + 유효 시간 설정 (Duration 사용)
     */
    @Override
    @Transactional
    public void setValuesWithTimeout(String key, String value, long timeoutMillis) {
        try {
            System.out.println("✅ Redis 저장 시도: " + key + " (TTL: " + timeoutMillis + "ms)");
            redisTemplate.opsForValue().set(key, value, Duration.ofMillis(timeoutMillis));
            System.out.println("✅ Redis 저장 성공 (TTL 적용): " + key);
        } catch (Exception e) {
            System.out.println("❌ Redis 저장 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ✅ 값 조회
     */
    @Override
    public String getValues(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            System.out.println("🔍 Redis 조회: " + key + " = " + value);
            return value;
        } catch (Exception e) {
            System.out.println("❌ Redis 조회 실패: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ✅ 값 삭제
     */
    @Override
    @Transactional
    public void deleteValues(String key) {
        try {
            redisTemplate.delete(key);
            System.out.println("🗑️ Redis 삭제: " + key);
        } catch (Exception e) {
            System.out.println("❌ Redis 삭제 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

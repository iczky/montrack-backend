package com.montrackBackend.montrack.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class AuthRedisRepository {
    private static final String STRING_KEY_PREFIX = "montrack:jwt:strings:";

    private final ValueOperations<String, String> valueOperations;


    public AuthRedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void saveJwtKey(String email, String jwtKey){
        valueOperations.set(STRING_KEY_PREFIX+email, jwtKey, 25, TimeUnit.SECONDS);
    }

    public String getJwtKey(String email){
        return valueOperations.get(STRING_KEY_PREFIX+email);
    }

    public void deleteJwtKey(String email){
        valueOperations.getAndDelete(STRING_KEY_PREFIX+email);
    }
}

package com.lotfresh.orderservice.domain.order.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String,String> redisTemplate;
    public void setValues(String key, String value, Duration duration) {
        ValueOperations<String,String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(key,value,duration);
    }

}

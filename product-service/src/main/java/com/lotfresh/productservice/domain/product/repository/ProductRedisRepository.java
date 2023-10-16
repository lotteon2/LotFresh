package com.lotfresh.productservice.domain.product.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRedisRepository {
  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  public List<BestProductVO> getBestProductsVO(String key) throws JsonProcessingException {
    List<BestProductVO> bestProductsVO =
        objectMapper.readValue(redisTemplate.opsForValue().get(key), new TypeReference<>() {});
    return bestProductsVO;
  }
}

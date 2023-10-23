package com.lotfresh.productservice.domain.product.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import com.lotfresh.productservice.domain.product.vo.SalesProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRedisRepository {
  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  public List<BestProductVO> getBestProductsVO(String key) throws JsonProcessingException {
    String stringValue = redisTemplate.opsForValue().get(key);
    if (isInValidValue(stringValue)) {
      return Collections.EMPTY_LIST;
    }
    List<BestProductVO> bestProductsVO =
        objectMapper.readValue(stringValue, new TypeReference<>() {});
    return bestProductsVO;
  }

  public List<SalesProductVO> getSalesProductsVO(String key) throws JsonProcessingException {
    String stringValue = redisTemplate.opsForValue().get(key);
    if (isInValidValue(stringValue)) {
      return Collections.EMPTY_LIST;
    }
    List<SalesProductVO> salesProductsVO =
        objectMapper.readValue(stringValue, new TypeReference<>() {});
    return salesProductsVO;
  }

  private Boolean isInValidValue(String key) {
    return key == null ? true : false;
  }
}

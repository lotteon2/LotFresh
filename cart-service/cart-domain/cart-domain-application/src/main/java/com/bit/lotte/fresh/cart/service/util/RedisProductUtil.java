package com.bit.lotte.fresh.cart.service.util;

import com.bit.lotte.fresh.cart.domain.entity.Product;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
public class RedisProductUtil {

  private final RedisTemplate redisTemplate;

 public void saveProduct(Product product) {
    log.info("redis product:{} ",product);
    redisTemplate.opsForValue().set("cart_" + product.getId(), product);
  }

}

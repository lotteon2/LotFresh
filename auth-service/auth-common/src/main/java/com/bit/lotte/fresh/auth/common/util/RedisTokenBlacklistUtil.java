package com.bit.lotte.fresh.auth.common.util;

import com.bit.lotte.fresh.auth.common.instant.TokenName;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisTokenBlacklistUtil {

  private static final String BLACKLIST_KEY = "tokenBlacklist";
  private final RedisTemplate<String, Object> redisTemplate;


  public void blacklistToken(String token) {
    log.info("token will be black-list:" + token);
    redisTemplate.opsForValue().set(token.substring(TokenName.BEARER_PREFIX_LENGTH), "invalid", 1, TimeUnit.DAYS);
  }

  public boolean isTokenBlacklisted(String token) {
    log.info("is redis closed: " + redisTemplate.getConnectionFactory().getConnection().isClosed());
    return Boolean.TRUE.equals(redisTemplate.hasKey(token));
  }
}

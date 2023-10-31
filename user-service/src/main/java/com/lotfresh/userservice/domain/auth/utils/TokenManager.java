package com.lotfresh.userservice.domain.auth.utils;

import com.lotfresh.userservice.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenManager {
    private final Environment env;
//    private final RedisTemplate redisTemplate;
    private final MemberRepository memberRepository;
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = env.getProperty("jwt.token.secret-key");
    }


    public String generateToken(Long uid, String role) {
        long tokenPeriod = Long.parseLong(
                env.getProperty("jwt.access-token.expire-length")); // 2 시간 설정해놓음

        Claims claims = Jwts.claims().setSubject(uid.toString());
        claims.put("userId", uid.toString());
        claims.put("role", role);

        Date now = new Date();

        final String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }
}

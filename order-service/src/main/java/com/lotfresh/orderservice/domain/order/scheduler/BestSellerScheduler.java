package com.lotfresh.orderservice.domain.order.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.orderservice.domain.order.redis.RedisService;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import com.lotfresh.orderservice.domain.order.service.response.BestProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BestSellerScheduler {
    private final RedisService redisService;
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    final Integer BEST_SELLER_CNT = 100;
    final Duration EXPIRATION_DATE = Duration.ofDays(30);

    // TODO : 에러처리, 테스트코드 작성, 서버 실행 시 한번 실행하기
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void saveBestSellerProducts() throws Exception {
        String key = LocalDate.now().toString();
        List<BestProductsResponse> bestSellers = orderService.getMostSoldProducts(BEST_SELLER_CNT);
        redisService.setValues(key,objectMapper.writeValueAsString(bestSellers),EXPIRATION_DATE);
    }

}

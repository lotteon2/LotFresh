package com.lotfresh.orderservice.domain.orchestrator.task;

import com.lotfresh.orderservice.domain.orchestrator.feigns.CartFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.BuyProductEmptyCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartTask implements Task{
    private final CartFeignClient cartFeignClient;
    private final BuyProductEmptyCommand buyProductEmptyCommand;
    private final Long userId;
    @Override
    public void work() {
        cartFeignClient.removeItems(buyProductEmptyCommand, userId);
    }
}

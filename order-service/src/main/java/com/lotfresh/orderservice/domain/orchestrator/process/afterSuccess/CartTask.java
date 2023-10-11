package com.lotfresh.orderservice.domain.orchestrator.process.afterSuccess;

import com.lotfresh.orderservice.domain.orchestrator.feigns.CartFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.CartRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartTask implements Task{
    private final CartFeignClient cartFeignClient;
    private final CartRequest cartRequest;
    @Override
    public void work() {
        cartFeignClient.removeItems(cartRequest);
    }
}

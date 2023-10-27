package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {
    private String province;
    private Long orderId;
    private List<ProductInfo> productInfos;

}

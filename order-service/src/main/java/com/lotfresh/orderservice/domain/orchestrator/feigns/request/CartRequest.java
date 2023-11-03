package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private List<Long> productIdList;
    private String province;
}

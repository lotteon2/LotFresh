package com.lotfresh.orderservice.domain.orchestrator.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipcode;
    private String roadAddress;
    private String detailAddress;
}

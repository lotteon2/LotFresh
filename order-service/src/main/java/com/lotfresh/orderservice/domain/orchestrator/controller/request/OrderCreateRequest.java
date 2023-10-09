package com.lotfresh.orderservice.domain.orchestrator.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    @Valid
    @NotNull(message = "productRequests cannot be null")
    @Size(min = 1, message = "productRequests cannot be empty")
    private List<ProductRequest> productRequests;
}

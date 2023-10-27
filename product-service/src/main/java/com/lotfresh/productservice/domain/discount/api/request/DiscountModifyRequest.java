package com.lotfresh.productservice.domain.discount.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class DiscountModifyRequest {
  @NotNull(message = "rate can not be null")
  private Double rate;

  @NotEmpty(message = "imgurl can not be empty")
  private String imgurl;
}

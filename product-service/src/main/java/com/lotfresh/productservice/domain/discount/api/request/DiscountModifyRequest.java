package com.lotfresh.productservice.domain.discount.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountModifyRequest {
  @NotNull(message = "rate can not be null")
  private Double rate;

  @NotBlank(message = "imgurl can not be blank")
  private String imgurl;
}

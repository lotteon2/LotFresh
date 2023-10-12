package com.lotfresh.productservice.domain.product.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModifyRequest {
  @NotNull(message = "categoryId can not be null")
  private Long categoryId;

  @NotEmpty(message = "name can not be empty")
  private String name;

  @NotEmpty(message = "thumbnail can not be empty")
  private String thumbnail;

  @NotEmpty(message = "detail can not be empty")
  private String detail;

  @NotNull(message = "price can not be null")
  private Integer price;

  @NotEmpty(message = "productCode can not be empty")
  private String productCode;
}

package com.lotfresh.productservice.domain.product.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModifyRequest {
  @NotNull(message = "categoryId can not be null")
  private Long categoryId;

  @NotBlank(message = "name can not be blank")
  private String name;

  @NotBlank(message = "thumbnail can not be blank")
  private String thumbnail;

  @NotBlank(message = "detail can not be blank")
  private String detail;

  @NotNull(message = "price can not be null")
  private Integer price;

  @NotBlank(message = "productCode can not be blank")
  private String productCode;
}

package com.lotfresh.productservice.domain.product.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
  private Long id;
  private String name;
  private String thumbnail;
  private String detail;
  private Integer price;
  private String productCode;
  private Integer stock;
  private Long categoryId;
  private String categoryName;
}

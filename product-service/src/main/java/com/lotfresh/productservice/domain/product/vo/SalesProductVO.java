package com.lotfresh.productservice.domain.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesProductVO {
  private Long productId;
  private Integer stock;
}

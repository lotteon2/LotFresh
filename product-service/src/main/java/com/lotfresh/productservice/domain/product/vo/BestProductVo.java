package com.lotfresh.productservice.domain.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// @NoArgsConstructor
@AllArgsConstructor
public class BestProductVo {
  private final Long id;
  private final Integer count;
}

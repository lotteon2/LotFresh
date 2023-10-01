package com.lotfresh.productservice.domain.discount.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class DiscountResponse {
  private Long id;
  private Double rate;
  private String imgurl;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long categoryId;
  private Long categoryName;

  @Builder
  private DiscountResponse(
      Long id,
      Double rate,
      String imgurl,
      LocalDate startDate,
      LocalDate endDate,
      Long categoryId,
      Long categoryName) {
    this.id = id;
    this.rate = rate;
    this.imgurl = imgurl;
    this.startDate = startDate;
    this.endDate = endDate;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }
}

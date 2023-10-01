package com.lotfresh.productservice.domain.discount.service.response;

import com.lotfresh.productservice.domain.discount.entity.Discount;
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
  private String categoryName;

  @Builder
  private DiscountResponse(
      Long id,
      Double rate,
      String imgurl,
      LocalDate startDate,
      LocalDate endDate,
      Long categoryId,
      String categoryName) {
    this.id = id;
    this.rate = rate;
    this.imgurl = imgurl;
    this.startDate = startDate;
    this.endDate = endDate;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }

  public static DiscountResponse of(Discount discount) {
    return DiscountResponse.builder()
        .id(discount.getId())
        .rate(discount.getRate())
        .imgurl(discount.getImgurl())
        .startDate(discount.getStartDate())
        .endDate(discount.getEndDate())
        .categoryId(discount.getCategory().getId())
        .categoryName(discount.getCategory().getName())
        .build();
  }
}

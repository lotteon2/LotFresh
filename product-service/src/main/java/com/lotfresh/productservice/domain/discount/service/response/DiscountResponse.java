package com.lotfresh.productservice.domain.discount.service.response;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class DiscountResponse implements Serializable {
  private Long id;
  private Double rate;
  private String imgurl;
  private String startDate;
  private String endDate;
  private Long categoryId;
  private String categoryName;

  @Builder
  private DiscountResponse(
      Long id,
      Double rate,
      String imgurl,
      String startDate,
      String endDate,
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

  public static DiscountResponse from(Discount discount) {
    return DiscountResponse.builder()
        .id(discount.getId())
        .rate(discount.getRate())
        .imgurl(discount.getImgurl())
        .startDate(discount.getStartDate().toString())
        .endDate(discount.getEndDate().toString())
        .categoryId(discount.getCategory().getId())
        .categoryName(discount.getCategory().getName())
        .build();
  }
}

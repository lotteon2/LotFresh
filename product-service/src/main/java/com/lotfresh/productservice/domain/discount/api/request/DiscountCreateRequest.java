package com.lotfresh.productservice.domain.discount.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCreateRequest {
  private Long categoryId;
  private Integer rate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd", timezone = "Asia/Seoul")
  private LocalDate startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd", timezone = "Asia/Seoul")
  private LocalDate endDate;

  private String imgurl;

  public Discount toEntity(Category category) {
    return Discount.builder()
        .category(category)
        .rate(rate)
        .startDate(startDate)
        .endDate(endDate)
        .imgurl(imgurl)
        .build();
  }
}

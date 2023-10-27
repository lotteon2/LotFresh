package com.lotfresh.productservice.domain.discount.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DiscountCreateRequest {

  @NotNull(message = "categoryId can not be null")
  private Long categoryId;

  @NotNull(message = "rate can not be null")
  private Double rate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  @NotNull(message = "startDate can not be null")
  private LocalDate startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  @NotNull(message = "endDate can not be null")
  private LocalDate endDate;

  @NotEmpty(message = "imgurl can not be empty")
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

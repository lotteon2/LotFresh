package com.lotfresh.productservice.domain.product.api.request;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ProductCreateRequest {
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

  public Product toEntity(Category category) {
    return Product.builder()
        .category(category)
        .name(name)
        .thumbnail(thumbnail)
        .detail(detail)
        .price(price)
        .productCode(productCode)
        .build();
  }
}

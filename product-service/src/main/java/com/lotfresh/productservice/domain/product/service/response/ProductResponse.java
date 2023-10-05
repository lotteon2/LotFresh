package com.lotfresh.productservice.domain.product.service.response;

import com.lotfresh.productservice.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductResponse {
  private Long id;
  private String name;
  private String thumbnail;
  private String detail;
  private Integer price;
  private String productCode;
  private Long categoryId;
  private String categoryName;
  private Integer stock;

  @Builder
  public ProductResponse(
      Long id,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      String productCode,
      Long categoryId,
      String categoryName,
      Integer stock) {
    this.id = id;
    this.name = name;
    this.thumbnail = thumbnail;
    this.detail = detail;
    this.price = price;
    this.productCode = productCode;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.stock = stock;
  }

  public static ProductResponse from(Product product, Integer stock) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .thumbnail(product.getThumbnail())
        .detail(product.getDetail())
        .price(product.getPrice())
        .productCode(product.getProductCode())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .stock(stock)
        .build();
  }
}

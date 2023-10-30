package com.lotfresh.productservice.domain.product.service.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import com.lotfresh.productservice.domain.product.vo.SalesProductVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductResponse {
  private Long id;
  private String name;
  private String thumbnail;
  private String detail;
  private Integer price;
  private Integer salesPrice;
  private String productCode;
  private Long categoryId;
  private String categoryName;
  @JsonIgnore private Long parentId;
  @JsonIgnore private String parentName;
  private Integer stock;

  @Builder
  private ProductResponse(
      Long id,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      Integer salesPrice,
      String productCode,
      Long categoryId,
      String categoryName,
      Long parentId,
      String parentName,
      Integer stock) {
    this.id = id;
    this.name = name;
    this.thumbnail = thumbnail;
    this.detail = detail;
    this.price = price;
    this.salesPrice = salesPrice;
    this.productCode = productCode;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.parentId = parentId;
    this.parentName = parentName;
    this.stock = stock;
  }

  public static ProductResponse of(Product product, Double discountRate, Integer stock) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .thumbnail(product.getThumbnail())
        .detail(product.getDetail())
        .price(product.getPrice())
        .salesPrice(
            discountRate == 0d
                ? null
                : product.getPrice() - (int) (product.getPrice() * (discountRate * 0.01)))
        .productCode(product.getProductCode())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .parentId(
            product.getCategory().getParent() != null
                ? product.getCategory().getParent().getId()
                : null)
        .parentName(
            product.getCategory().getParent() != null
                ? product.getCategory().getParent().getName()
                : null)
        .stock(stock)
        .build();
  }

  public static ProductResponse of(Product product, Double discountRate) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .thumbnail(product.getThumbnail())
        .detail(product.getDetail())
        .price(product.getPrice())
        .salesPrice(
            discountRate == 0d
                ? null
                : product.getPrice() - (int) (product.getPrice() * (discountRate * 0.01)))
        .productCode(product.getProductCode())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .build();
  }

  public static List<ProductResponse> createBestProductResponses(
      List<BestProductVO> bestProductsVo,
      Map<Long, Product> productMap,
      Map<Long, Double> rateGroupByCategory) {
    return bestProductsVo.stream()
        .sorted(Comparator.comparing(BestProductVO::getCnt).reversed())
        .map(
            vo -> {
              Product product = productMap.get(vo.getProductId());
              return ProductResponse.of(
                  product, rateGroupByCategory.getOrDefault(product.getCategory().getId(), 0d));
            })
        .collect(Collectors.toList());
  }

  public static List<ProductResponse> createSalesProductResponses(
      List<SalesProductVO> salesProductsVO, Map<Long, Product> productMap) {
    return salesProductsVO.stream()
        .sorted(Comparator.comparing(SalesProductVO::getStock))
        .map(
            vo -> {
              Product product = productMap.get(vo.getProductId());
              return ProductResponse.of(product, 50d, vo.getStock());
            })
        .collect(Collectors.toList());
  }

  public static List<ProductResponse> createNewProductResponse(
      List<Product> newProducts, Map<Long, Double> rateGroupByCategory) {
    return newProducts.stream()
        .map(
            product ->
                ProductResponse.of(
                    product, rateGroupByCategory.getOrDefault(product.getCategory().getId(), 0d)))
        .collect(Collectors.toList());
  }
}

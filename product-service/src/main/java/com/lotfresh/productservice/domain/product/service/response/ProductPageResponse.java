package com.lotfresh.productservice.domain.product.service.response;

import com.lotfresh.productservice.domain.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductPageResponse {
  private List<ProductResponse> products;
  private Integer totalPage;
  private Long totalElements;

  private ProductPageResponse(
      List<ProductResponse> products, Integer totalPage, Long totalElements) {
    this.products = products;
    this.totalPage = totalPage;
    this.totalElements = totalElements;
  }

  public static ProductPageResponse of(
      Page<Product> productPage, Map<Long, Double> rateGroupByCategory) {
    List<ProductResponse> products =
        productPage.getContent().stream()
            .map(
                product ->
                    ProductResponse.of(
                        product,
                        rateGroupByCategory.getOrDefault(product.getCategory().getId(), 0d)))
            .collect(Collectors.toList());
    return new ProductPageResponse(
        products, productPage.getTotalPages(), productPage.getTotalElements());
  }
}

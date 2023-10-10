package com.lotfresh.productservice.domain.product.service.response;

import com.lotfresh.productservice.domain.product.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ProductPageResponse {
  private List<ProductResponse> products;
  private Integer totalPage;

  private ProductPageResponse(Integer totalPage, List<ProductResponse> products) {
    this.totalPage = totalPage;
    this.products = products;
  }

  public static ProductPageResponse of(Page<Product> productPage, Double discountRate) {
    final List<ProductResponse> productResponses =
        productPage.getContent().stream()
            .map(product -> ProductResponse.of(product, discountRate))
            .collect(Collectors.toList());
    return new ProductPageResponse(productPage.getTotalPages(), productResponses);
  }
}

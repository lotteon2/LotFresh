package com.lotfresh.productservice.domain.product.api;

import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApiController {
  private final ProductService productService;

  @PostMapping("")
  public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductCreateRequest request) {
    return ResponseEntity.ok(productService.createProduct(request));
  }
}

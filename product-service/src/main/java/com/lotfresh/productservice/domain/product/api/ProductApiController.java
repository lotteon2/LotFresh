package com.lotfresh.productservice.domain.product.api;

import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductApiController {
  private final ProductService productService;

  @PostMapping("")
  public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<Void> modifyProduct(
      @Valid @RequestBody ProductModifyRequest request, @PathVariable("productId") Long productId) {
    productService.modifyProduct(request, productId);
    return ResponseEntity.ok().build();
  }
}

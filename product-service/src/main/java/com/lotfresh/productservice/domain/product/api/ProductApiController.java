package com.lotfresh.productservice.domain.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.feign.StorageApiClient;
import com.lotfresh.productservice.domain.product.service.ProductService;
import com.lotfresh.productservice.domain.product.service.response.ProductPageResponse;
import com.lotfresh.productservice.domain.product.service.response.ProductResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductApiController {
  private final ProductService productService;
  private final StorageApiClient storageApiClient;

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

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> softDeleteProduct(@PathVariable("productId") Long productId) {
    productService.softDelete(productId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{productId}/{province}")
  public ResponseEntity<ProductResponse> getProductDetail(
      @RequestHeader(value = "userId", required = false) Long userId,
      @PathVariable(value = "province", required = false) String province,
      @PathVariable("productId") Long productId) {
    Integer stock = null;
    try {
      stock = storageApiClient.getStock(userId, province, productId);
    } catch (FeignException e) {
      log.error(e.getMessage());
    }
    return ResponseEntity.ok(productService.getProductDetail(productId, stock));
  }

  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<ProductPageResponse> getProductsByCategory(
      @ModelAttribute PageRequest pageRequest, @PathVariable("categoryId") Long categoryId) {
    return ResponseEntity.ok(productService.getProductsByCategory(categoryId, pageRequest));
  }

  @GetMapping("/best-products")
  public ResponseEntity<List<ProductResponse>> getBestProducts() throws JsonProcessingException {
    return ResponseEntity.ok(productService.getBestProducts());
  }

  @GetMapping("/sales-products/{province}")
  public ResponseEntity<List<ProductResponse>> getSalesProducts(
      @PathVariable("province") String province) throws JsonProcessingException {
    return ResponseEntity.ok(productService.getSalesProducts(province));
  }

  @GetMapping("/{productId}/province/{province}/sales")
  public ResponseEntity<ProductResponse> getSalesProductDetails(
      @PathVariable("productId") Long productId,
      @PathVariable(value = "userId", required = false) String province) {
    Integer stock = null;
    try {
      stock = storageApiClient.getSalesProductStock(province, productId);
    } catch (FeignException e) {
    }
    return ResponseEntity.ok(productService.getSalesProductDetails(productId, stock));
  }

  @GetMapping("/new-products")
  public ResponseEntity<List<ProductResponse>> getNewProducts() {
    return ResponseEntity.ok(productService.getNewProducts());
  }

  @GetMapping("")
  public ResponseEntity<ProductPageResponse> getProductsByKeyword(
      @ModelAttribute PageRequest pageRequest) {
    return ResponseEntity.ok(productService.getProductsByKeyword(pageRequest));
  }
}

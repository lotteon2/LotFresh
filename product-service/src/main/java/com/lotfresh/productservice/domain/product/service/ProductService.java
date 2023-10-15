package com.lotfresh.productservice.domain.product.service;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.exception.ProductNotFound;
import com.lotfresh.productservice.domain.product.repository.ProductRepository;
import com.lotfresh.productservice.domain.product.service.response.ProductPageResponse;
import com.lotfresh.productservice.domain.product.service.response.ProductResponse;
import com.lotfresh.productservice.domain.product.vo.BestProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final DiscountRepository discountRepository;
  private final RedisTemplate<String, List<BestProductVo>> redisTemplate;

  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

  @Transactional
  public Long createProduct(ProductCreateRequest request) {
    Category category =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(() -> new CategoryNotFound());
    Product product = request.toEntity(category);
    Product savedProduct = productRepository.save(product);
    return savedProduct.getId();
  }

  @Transactional
  public void modifyProduct(ProductModifyRequest request, Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound());

    Category category =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(() -> new CategoryNotFound());

    product.changeProduct(
        category,
        request.getName(),
        request.getThumbnail(),
        request.getDetail(),
        request.getPrice(),
        request.getProductCode());
  }

  @Transactional
  public void softDelete(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound());
    product.softDelete();
  }

  public ProductResponse getProductDetail(Long id, Integer stock) {
    Product product = productRepository.findByIdFetch(id).orElseThrow(() -> new ProductNotFound());
    Long categoryId = product.getCategory().getId();
    Double discountRate = getDiscountRateByCategory(categoryId);
    return ProductResponse.of(product, discountRate, stock);
  }

  public ProductPageResponse getProductsByCategory(Long categoryId, PageRequest pageRequest) {
    Page<Product> productPage = productRepository.findAllByCategory(categoryId, pageRequest);
    Map<Long, Double> rateGroupByCategory = discountRepository.findRateGroupByCategory();
    return ProductPageResponse.of(productPage, rateGroupByCategory);
  }

  public List<ProductResponse> getBestProducts() {

    List<BestProductVo> bestProductsVo =
        redisTemplate.opsForValue().get(format.format(LocalDate.now()));

    if (bestProductsVo.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    List<Long> bestProductIds = extractBestProductIds(bestProductsVo);
    Map<Long, Product> productMap = getProductMapById(bestProductIds);
    Map<Long, Double> rateGroupByCategory = discountRepository.findRateGroupByCategory();

    return ProductResponse.createProductResponses(bestProductsVo, productMap, rateGroupByCategory);
  }

  private Map<Long, Product> getProductMapById(List<Long> bestProductIds) {
    return productRepository.findBestProducts(bestProductIds).stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
  }

  private List<Long> extractBestProductIds(List<BestProductVo> bestProductsVo) {
    return bestProductsVo.stream().map(best -> best.getId()).collect(Collectors.toList());
  }

  private Double getDiscountRateByCategory(Long categoryId) {
    return discountRepository.findRateByCategoryId(categoryId).orElse(0d);
  }
}

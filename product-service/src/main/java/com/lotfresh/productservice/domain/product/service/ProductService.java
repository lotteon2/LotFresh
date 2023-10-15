package com.lotfresh.productservice.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

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

  // TODO redis 로직 분리 (재사용을 위함)
  public List<ProductResponse> getBestProducts() throws JsonProcessingException {
    List<BestProductVO> bestProductsVO =
        objectMapper.readValue(
            redisTemplate
                .opsForValue()
                .get(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
            new TypeReference<>() {});

    if (bestProductsVO.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    Map<Long, Product> productMap = extractProductMapByVO(bestProductsVO);
    Map<Long, Double> rateGroupByCategory = discountRepository.findRateGroupByCategory();

    return ProductResponse.createProductResponses(bestProductsVO, productMap, rateGroupByCategory);
  }

  private Map<Long, Product> extractProductMapByVO(List<BestProductVO> bestProductsVO) {

    List<Long> bestProductIds =
        bestProductsVO.stream().map(best -> best.getProductId()).collect(Collectors.toList());
    return productRepository.findBestProducts(bestProductIds).stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
  }

  private Double getDiscountRateByCategory(Long categoryId) {
    return discountRepository.findRateByCategoryId(categoryId).orElse(0d);
  }
}

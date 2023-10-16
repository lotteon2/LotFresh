package com.lotfresh.productservice.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.exception.ProductNotFound;
import com.lotfresh.productservice.domain.product.repository.ProductRedisRepository;
import com.lotfresh.productservice.domain.product.repository.ProductRepository;
import com.lotfresh.productservice.domain.product.service.response.ProductPageResponse;
import com.lotfresh.productservice.domain.product.service.response.ProductResponse;
import com.lotfresh.productservice.domain.product.vo.BestProductVO;
import com.lotfresh.productservice.domain.product.vo.SalesProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final ProductRedisRepository redisRepository;

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

  public List<ProductResponse> getBestProducts() throws JsonProcessingException {
    List<BestProductVO> bestProductsVO =
        redisRepository.getBestProductsVO(LocalDate.now().toString());

    if (bestProductsVO.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    Map<Long, Product> productMap = extractProductMapByBestProductVO(bestProductsVO);
    Map<Long, Double> rateGroupByCategory = discountRepository.findRateGroupByCategory();

    return ProductResponse.createBestProductResponses(bestProductsVO, productMap, rateGroupByCategory);
  }

  public List<ProductResponse> getSalesProducts(String memberAddressKey)
      throws JsonProcessingException {
    List<SalesProductVO> salesProductsVO = redisRepository.getSalesProductsIds(memberAddressKey);

    if (salesProductsVO.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    Map<Long, Product> productMap = extractProductMapBySalesProductVO(salesProductsVO);

    return ProductResponse.createSalesProductResponses(salesProductsVO, productMap);
  }

  private Map<Long, Product> extractProductMapByBestProductVO(List<BestProductVO> bestProductsVO) {
    List<Long> bestProductIds =
        bestProductsVO.stream().map(best -> best.getProductId()).collect(Collectors.toList());
    return extractProductMapByIds(bestProductIds);
  }

  private Map<Long, Product> extractProductMapBySalesProductVO(List<SalesProductVO> salesProductsVO) {
    List<Long> salesProductIds =
            salesProductsVO.stream().map(sales -> sales.getProductId()).collect(Collectors.toList());
    return extractProductMapByIds(salesProductIds);
  }

  private Map<Long, Product> extractProductMapByIds(List<Long> productIds) {
    return productRepository.findAllByIds(productIds).stream()
        .collect(Collectors.toMap(Product::getId, Function.identity()));
  }

  private Double getDiscountRateByCategory(Long categoryId) {
    return discountRepository.findRateByCategoryId(categoryId).orElse(0d);
  }
}

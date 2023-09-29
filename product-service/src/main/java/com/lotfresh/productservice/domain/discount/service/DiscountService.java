package com.lotfresh.productservice.domain.discount.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiscountService {
  private final DiscountRepository discountRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public Long createDiscount(DiscountCreateRequest request) {
    Long categoryId = request.getCategoryId();
    Category getCategory =
        categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound());
    Discount discount = request.toEntity(getCategory);
    Discount savedDiscount = discountRepository.save(discount);
    return savedDiscount.getId();
  }
}

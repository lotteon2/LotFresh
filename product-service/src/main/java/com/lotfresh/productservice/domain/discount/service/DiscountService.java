package com.lotfresh.productservice.domain.discount.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.api.request.DiscountModifyRequest;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.exception.DiscountNotFound;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.discount.service.response.DiscountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        categoryRepository.findById(categoryId).orElseThrow(CategoryNotFound::new);
    Discount discount = request.toEntity(getCategory);
    Discount savedDiscount = discountRepository.save(discount);
    return savedDiscount.getId();
  }

  @Transactional
  public void modifyDiscount(DiscountModifyRequest request, Long id) {
    Discount discount = discountRepository.findById(id).orElseThrow(DiscountNotFound::new);
    discount.changeDiscount(request.getRate(), request.getImgurl());
  }

  public DiscountResponse getDiscount(Long id) {
    Discount discount =
        discountRepository.findByIdFetch(id).orElseThrow(DiscountNotFound::new);
    DiscountResponse discountResponse = DiscountResponse.from(discount);
    return discountResponse;
  }

  public List<DiscountResponse> getDiscounts() {
    List<Discount> discountList = discountRepository.findAllFetch();
    return discountList.stream().map(DiscountResponse::from).collect(Collectors.toList());
  }
}

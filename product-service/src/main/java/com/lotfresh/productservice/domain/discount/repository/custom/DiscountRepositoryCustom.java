package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountRepositoryCustom {
  Optional<Discount> findByIdFetch(Long id);

  Optional<Discount> findByCategoryId(Long categoryId);

  Optional<Double> findRateByCategoryId(Long categoryId);

  List<Discount> findAllFetch();

  Map<Long, Double> findRateGroupByCategory();
}

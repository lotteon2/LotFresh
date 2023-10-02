package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountRepositoryCustom {
  Optional<Discount> findByIdFetch(Long id);

  List<Discount> findAllEager();
}

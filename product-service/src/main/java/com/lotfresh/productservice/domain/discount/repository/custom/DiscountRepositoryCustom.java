package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;

import java.util.Optional;

public interface DiscountRepositoryCustom {
  Optional<Discount> findByIdQuery(Long id);
}

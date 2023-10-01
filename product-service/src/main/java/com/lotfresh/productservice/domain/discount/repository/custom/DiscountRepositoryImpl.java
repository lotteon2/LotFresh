package com.lotfresh.productservice.domain.discount.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepositoryCustom {
  private final JPAQueryFactory query;
}

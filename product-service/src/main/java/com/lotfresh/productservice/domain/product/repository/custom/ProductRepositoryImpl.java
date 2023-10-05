package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.domain.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lotfresh.productservice.domain.product.entity.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public Optional<Product> findByIdFetch(Long id) {
    return Optional.ofNullable(
        query
            .selectFrom(product)
            .join(product.category)
            .fetchJoin()
            .where(product.id.eq(id))
            .fetchOne());
  }
}

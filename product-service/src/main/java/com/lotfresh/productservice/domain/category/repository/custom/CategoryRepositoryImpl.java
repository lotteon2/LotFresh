package com.lotfresh.productservice.domain.category.repository.custom;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lotfresh.productservice.domain.category.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public Optional<Category> findByIdQuery(Long categoryId) {
    Category fetchOne =
        query
            .selectFrom(category)
            .distinct()
            .leftJoin(category.children)
            .fetchJoin()
            .where(category.id.eq(categoryId))
            .fetchOne();
    return Optional.ofNullable(fetchOne);
  }
}

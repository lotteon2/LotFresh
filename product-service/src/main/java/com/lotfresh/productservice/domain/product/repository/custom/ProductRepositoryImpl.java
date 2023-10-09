package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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

  @Override
  public PageImpl<Product> findAllByCategory(Long categoryId, PageRequest pageRequest) {
    List<Long> ids =
        query
            .select(product.id)
            .from(product)
            .where(product.category.id.eq(categoryId), getSearchCondition(pageRequest))
            .offset(pageRequest.getPageable().getOffset())
            .limit(pageRequest.getPageable().getPageSize())
            .orderBy(getOrderCondition(pageRequest.getPageable()))
            .fetch();

    if (CollectionUtils.isEmpty(ids)) {
      return new PageImpl<>(
          new ArrayList<>(), pageRequest.getPageable(), getTotalPageCount(pageRequest));
    }

    List<Product> fetch =
        query
            .selectFrom(product)
            .join(product.category)
            .fetchJoin()
            .where(product.id.in(ids))
            .orderBy(getOrderCondition(pageRequest.getPageable()))
            .fetch();

    return new PageImpl<>(fetch, pageRequest.getPageable(), getTotalPageCount(pageRequest));
  }

  private Long getTotalPageCount(PageRequest pageRequest) {
    return query
        .select(product.count())
        .from(product)
        .where(getSearchCondition(pageRequest))
        .fetchOne();
  }

  private BooleanExpression getSearchCondition(PageRequest pageRequest) {
    if ("".equals(pageRequest.getKeyword())) {
      return null;
    }
    return product.name.contains(pageRequest.getKeyword());
  }

  private OrderSpecifier getOrderCondition(Pageable pageable) {
    Sort.Order order = pageable.getSort().iterator().next();
    String property = order.getProperty();
    switch (property) {
      default:
        return product.id.desc();
    }
  }
}

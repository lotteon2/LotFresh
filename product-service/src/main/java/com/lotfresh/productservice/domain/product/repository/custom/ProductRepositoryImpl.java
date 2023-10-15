package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
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
  public Page<Product> findAllByCategory(Long categoryId, PageRequest pageRequest) {

    List<Long> ids =
        query
            .select(product.id)
            .from(product)
            .where(
                product.category.id.eq(categoryId).or(product.category.parent.id.eq(categoryId)),
                keywordEq(pageRequest.getKeyword()),
                product.isDeleted.isFalse())
            .offset(pageRequest.getPageable().getOffset())
            .limit(pageRequest.getPageable().getPageSize())
            .orderBy(
                getOrderCondition(pageRequest.getPageable().getSort()).stream()
                    .toArray(OrderSpecifier[]::new))
            .fetch();

    if (CollectionUtils.isEmpty(ids)) {
      return new PageImpl<>(new ArrayList<>(), pageRequest.getPageable(), 0);
    }

    List<Product> fetch =
        query
            .selectFrom(product)
            .join(product.category)
            .fetchJoin()
            .where(product.id.in(ids))
            .orderBy(
                getOrderCondition(pageRequest.getPageable().getSort()).stream()
                    .toArray(OrderSpecifier[]::new))
            .fetch();

    return PageableExecutionUtils.getPage(
        fetch, pageRequest.getPageable(), () -> getTotalPageCount(pageRequest.getKeyword()));
  }

  @Override
  public List<Product> findBestProducts(List<Long> ids) {
    return query
        .selectFrom(product)
        .join(product.category)
        .fetchJoin()
        .where(product.id.in(ids), product.isDeleted.isFalse())
        .fetch();
  }

  private Long getTotalPageCount(String keyword) {
    return query
        .select(product.count())
        .from(product)
        .where(keywordEq(keyword), product.isDeleted.isFalse())
        .fetchOne();
  }

  private BooleanExpression keywordEq(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return null;
    }
    return product.name.contains(keyword);
  }

  private List<OrderSpecifier> getOrderCondition(Sort sort) {
    List<OrderSpecifier> orders = new ArrayList<>();
    sort.stream()
        .forEach(
            order -> {
              Order direction = order.isAscending() ? Order.ASC : Order.DESC;
              String property = order.getProperty();
              PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
              orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
            });
    return orders;
  }
}
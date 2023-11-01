package com.lotfresh.userservice.domain.member.repository.custom;

import com.lotfresh.userservice.common.paging.PageRequest;
import com.lotfresh.userservice.domain.member.entity.Member;
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

import static com.lotfresh.userservice.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public Optional<Member> findByEmail(String email) {
    return Optional.ofNullable(query.selectFrom(member).where(member.email.eq(email)).fetchOne());
  }

  @Override
  public Page<Member> findAllWithPaging(PageRequest pageRequest) {

    List<Long> ids =
        query
            .select(member.id)
            .from(member)
            .where(keywordEq(pageRequest.getKeyword()))
            .offset(pageRequest.getPageable().getOffset())
            .limit(pageRequest.getPageable().getPageSize())
            .orderBy(
                getOrderCondition(pageRequest.getPageable().getSort()).stream()
                    .toArray(OrderSpecifier[]::new))
            .fetch();

    List<Member> fetch =
        query
            .selectFrom(member)
            .where(member.id.in(ids))
            .orderBy(
                getOrderCondition(pageRequest.getPageable().getSort()).stream()
                    .toArray(OrderSpecifier[]::new))
            .fetch();

    if (CollectionUtils.isEmpty(ids)) {
      return new PageImpl<>(new ArrayList<>(), pageRequest.getPageable(), 0);
    }

    return PageableExecutionUtils.getPage(
        fetch, pageRequest.getPageable(), () -> getTotalPageCount(pageRequest.getKeyword()));
  }

  private Long getTotalPageCount(String keyword) {
    return query.select(member.count()).from(member).where(keywordEq(keyword)).fetchOne();
  }

  private BooleanExpression keywordEq(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return null;
    }
    return member.nickname.contains(keyword);
  }

  private List<OrderSpecifier> getOrderCondition(Sort sort) {
    List<OrderSpecifier> orders = new ArrayList<>();
    sort.stream()
        .forEach(
            order -> {
              Order direction = order.isAscending() ? Order.ASC : Order.DESC;
              String property = order.getProperty();
              PathBuilder orderByExpression = new PathBuilder(Member.class, "member");
              orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
            });
    return orders;
  }
}

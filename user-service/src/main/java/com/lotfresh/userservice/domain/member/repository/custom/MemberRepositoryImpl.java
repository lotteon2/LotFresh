package com.lotfresh.userservice.domain.member.repository.custom;

import com.lotfresh.userservice.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lotfresh.userservice.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public Optional<Member> findByEmail(String email) {
    return Optional.ofNullable(query.selectFrom(member).where(member.email.eq(email)).fetchOne());
  }
}

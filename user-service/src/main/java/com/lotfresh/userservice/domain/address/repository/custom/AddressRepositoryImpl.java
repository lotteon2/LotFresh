package com.lotfresh.userservice.domain.address.repository.custom;

import com.lotfresh.userservice.domain.address.entity.Address;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lotfresh.userservice.domain.address.entity.QAddress.address;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {
  private final JPAQueryFactory query;

  @Override
  public Optional<Address> findDefaultAddressByMemberId(Long memberId) {
    return Optional.ofNullable(
        query
            .selectFrom(address)
            .where(address.member.id.eq(memberId), address.defaultAddress.isTrue())
            .fetchOne());
  }
}

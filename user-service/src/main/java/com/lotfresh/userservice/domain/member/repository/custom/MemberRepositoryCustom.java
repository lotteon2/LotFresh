package com.lotfresh.userservice.domain.member.repository.custom;

import com.lotfresh.userservice.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

  Optional<Member> findByEmail(String email);
}

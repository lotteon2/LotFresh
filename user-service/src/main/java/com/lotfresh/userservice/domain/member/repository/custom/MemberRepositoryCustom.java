package com.lotfresh.userservice.domain.member.repository.custom;

import com.lotfresh.userservice.common.paging.PageRequest;
import com.lotfresh.userservice.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MemberRepositoryCustom {

  Optional<Member> findByEmail(String email);

  Page<Member> findAllWithPaging(PageRequest pageRequest);

}

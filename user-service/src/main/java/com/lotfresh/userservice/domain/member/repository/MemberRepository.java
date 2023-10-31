package com.lotfresh.userservice.domain.member.repository;

import com.lotfresh.userservice.domain.member.entity.Member;
import com.lotfresh.userservice.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {}

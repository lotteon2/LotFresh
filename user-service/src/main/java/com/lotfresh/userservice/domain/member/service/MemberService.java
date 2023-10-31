package com.lotfresh.userservice.domain.member.service;

import com.lotfresh.userservice.domain.member.entity.Member;
import com.lotfresh.userservice.domain.member.exception.MemberNotFound;
import com.lotfresh.userservice.domain.member.repository.MemberRepository;
import com.lotfresh.userservice.domain.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  public MemberResponse getMemberDetail(Long userId) {
    Member member = memberRepository.findById(userId).orElseThrow(MemberNotFound::new);
    return MemberResponse.from(member);
  }
}

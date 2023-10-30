package com.lotfresh.userservice.domain.member.service;

import com.lotfresh.userservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
  private MemberRepository memberRepository;
}

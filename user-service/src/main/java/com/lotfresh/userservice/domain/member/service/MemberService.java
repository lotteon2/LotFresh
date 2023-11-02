package com.lotfresh.userservice.domain.member.service;

import com.lotfresh.userservice.common.paging.PageRequest;
import com.lotfresh.userservice.domain.address.entity.Address;
import com.lotfresh.userservice.domain.address.repository.AddressRepository;
import com.lotfresh.userservice.domain.member.api.request.MemberCreateRequest;
import com.lotfresh.userservice.domain.member.entity.Member;
import com.lotfresh.userservice.domain.member.exception.MemberNotFound;
import com.lotfresh.userservice.domain.member.repository.MemberRepository;
import com.lotfresh.userservice.domain.member.service.response.MemberDetailPageResponse;
import com.lotfresh.userservice.domain.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
  private final MemberRepository memberRepository;
  private final AddressRepository addressRepository;

  public MemberResponse getMemberDetail(Long userId) {
    Member member = memberRepository.findById(userId).orElseThrow(MemberNotFound::new);
    Address address = addressRepository.findDefaultAddressByMemberId(member.getId()).orElse(null);
    return MemberResponse.of(member, address);
  }

  @Transactional
  public String createMember(MemberCreateRequest request, Long userId) {
    Member member = memberRepository.findById(userId).orElseThrow(MemberNotFound::new);
    Address address = request.toAddressEntity(member);
    addressRepository.save(address);
    address.changeDefaultAddress();
    member.changeActive();
    return address.getProvince();
  }

  public MemberDetailPageResponse getMemberPage(PageRequest pageRequest) {
    Page<Member> memberPage = memberRepository.findAllWithPaging(pageRequest);
    return MemberDetailPageResponse.from(memberPage);
  }
}

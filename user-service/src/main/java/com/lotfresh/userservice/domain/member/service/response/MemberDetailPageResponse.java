package com.lotfresh.userservice.domain.member.service.response;

import com.lotfresh.userservice.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberDetailPageResponse {
  private Integer totalPages;
  private Long totalElements;
  private List<MemberDetailResponse> memberDetailResponses;

  private MemberDetailPageResponse(
      Integer totalPages, Long totalElements, List<MemberDetailResponse> memberDetailResponses) {
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.memberDetailResponses = memberDetailResponses;
  }

  public static MemberDetailPageResponse from(Page<Member> memberPage) {
    List<MemberDetailResponse> memberDetailResponses =
        memberPage.getContent().stream()
            .map(MemberDetailResponse::from)
            .collect(Collectors.toList());

    return new MemberDetailPageResponse(
        memberPage.getTotalPages(), memberPage.getTotalElements(), memberDetailResponses);
  }
}

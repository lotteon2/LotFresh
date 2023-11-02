package com.lotfresh.userservice.domain.member.api;

import com.lotfresh.userservice.common.paging.PageRequest;
import com.lotfresh.userservice.domain.member.api.request.MemberCreateRequest;
import com.lotfresh.userservice.domain.member.service.MemberService;
import com.lotfresh.userservice.domain.member.service.response.MemberDetailPageResponse;
import com.lotfresh.userservice.domain.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping("/me")
  public ResponseEntity<MemberResponse> getMemberDetail(@RequestHeader("userId") Long userId) {
    return ResponseEntity.ok(memberService.getMemberDetail(userId));
  }

  @PostMapping("")
  public ResponseEntity<String> createMember(
      @RequestBody MemberCreateRequest request, @RequestHeader("userId") Long userId) {
    return ResponseEntity.ok(memberService.createMember(request, userId));
  }

  @GetMapping("")
  public ResponseEntity<MemberDetailPageResponse> getMembersWithPage(
      @ModelAttribute PageRequest pageRequest) {
    return ResponseEntity.ok(memberService.getMemberPage(pageRequest));
  }
}

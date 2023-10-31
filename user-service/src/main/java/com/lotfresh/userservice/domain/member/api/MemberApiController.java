package com.lotfresh.userservice.domain.member.api;

import com.lotfresh.userservice.domain.member.service.MemberService;
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
}

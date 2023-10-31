package com.lotfresh.userservice.domain.member.exception;

import com.lotfresh.userservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MemberNotFound extends CustomException {

  public static final String MESSAGE = "해당 멤버는 존재하지 않습니다.";

  public MemberNotFound() {
    super(MESSAGE);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }
}

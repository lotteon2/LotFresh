package com.lotfresh.productservice.domain.discount.exception;

import com.lotfresh.productservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DiscountNotFound extends CustomException {
  public static final String MESSAGE = "해당 카테고리 할인이 존재하지 않습니다.";

  public DiscountNotFound() {
    super(MESSAGE);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }
}

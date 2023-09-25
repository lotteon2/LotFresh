package com.lotfresh.productservice.domain.category.api.exception;

import com.lotfresh.productservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CategoryNotFound extends CustomException {

  public static final String MESSAGE = "해당 카테고리가 존재하지 않습니다.";

  public CategoryNotFound() {
    super(MESSAGE);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }
}

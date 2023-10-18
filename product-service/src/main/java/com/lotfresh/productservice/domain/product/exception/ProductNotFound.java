package com.lotfresh.productservice.domain.product.exception;

import com.lotfresh.productservice.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ProductNotFound extends CustomException {

  public static final String MESSAGE = "해당 상품이 존재하지 않습니다.";

  public ProductNotFound() {
    super(MESSAGE);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }
}

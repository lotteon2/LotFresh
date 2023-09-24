package com.lotfresh.productservice.exception.response;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

  private final Integer code;

  private final String message;

  private final Map<String, String> validation;

  @Builder
  public ErrorResponse(Integer code, String message, Map<String, String> validation) {
    this.code = code;
    this.message = message;
    this.validation = (validation != null ? validation : new HashMap<>());
  }

  public void addValidation(String field, String errorMessage) {
    this.validation.put(field, errorMessage);
  }
}

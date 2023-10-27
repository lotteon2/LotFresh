package com.lotfresh.productservice.domain.category.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class CategoryModifyRequest {
  private Long parentId;

  @NotEmpty(message = "name cannot be null")
  private String name;
}

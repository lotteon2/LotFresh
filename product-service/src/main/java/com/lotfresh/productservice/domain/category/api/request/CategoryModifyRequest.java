package com.lotfresh.productservice.domain.category.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyRequest {
  private Long parentId;

  @NotEmpty(message = "name cannot be null")
  private String name;
}

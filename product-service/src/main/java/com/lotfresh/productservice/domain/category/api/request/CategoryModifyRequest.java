package com.lotfresh.productservice.domain.category.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModifyRequest {
  private Long parentId;

  @NotEmpty(message = "name cannot be null")
  private String name;
}

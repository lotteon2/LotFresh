package com.lotfresh.productservice.domain.category.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyRequest {
  private Long parentId;

  @NotBlank(message = "name cannot be blank")
  private String name;
}

package com.lotfresh.productservice.domain.category.api.request;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
  private Long parentId;

  @NotEmpty(message = "name cannot be null")
  private String name;

  public Category toEntity(Category parent) {
    return Category.builder().parent(parent).name(name).build();
  }
}

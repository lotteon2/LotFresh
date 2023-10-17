package com.lotfresh.productservice.domain.category.api.request;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
  private Long parentId;

  @NotBlank(message = "name cannot be blank")
  private String name;

  public Category toEntity(Category parent) {
    return Category.builder().parent(parent).name(name).build();
  }
}

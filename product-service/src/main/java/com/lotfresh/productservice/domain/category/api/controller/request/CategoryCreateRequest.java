package com.lotfresh.productservice.domain.category.api.controller.request;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {
  private Long parentId;
  private String name;

  public Category toEntity(Category parent) {
    return Category.builder().parent(parent).name(name).build();
  }
}

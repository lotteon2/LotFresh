package com.lotfresh.productservice.api.controller.request;

import com.lotfresh.productservice.domain.category.Category;
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

  public Category toEntity(Optional<Category> parent) {
    return Category.builder().parent(parent.isEmpty() ? null : parent.get()).name(name).build();
  }
}

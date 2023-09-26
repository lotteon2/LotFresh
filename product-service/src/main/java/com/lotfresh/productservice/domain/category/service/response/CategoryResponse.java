package com.lotfresh.productservice.domain.category.service.response;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
public class CategoryResponse {
  private Long id;
  private String name;
  private List<CategoryResponse> children;

  @Builder
  private CategoryResponse(Long id, String name, List<CategoryResponse> children) {
    this.id = id;
    this.name = name;
    this.children = children;
  }

  public static CategoryResponse of(Category category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .children(
            category.getChildren().stream().map(CategoryResponse::of).collect(Collectors.toList()))
        .build();
  }
}

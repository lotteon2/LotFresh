package com.lotfresh.productservice.domain.category.service.response;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
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

  public static CategoryResponse from(Category category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .children(
            category.getChildren().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList()))
        .build();
  }
}

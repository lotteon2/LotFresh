package com.lotfresh.productservice.domain.category.api;

import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryApiController {
  private final CategoryService categoryService;

  @PostMapping("")
  public ResponseEntity<Long> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
    Long categoryId = categoryService.createCategory(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryId);
  }

}

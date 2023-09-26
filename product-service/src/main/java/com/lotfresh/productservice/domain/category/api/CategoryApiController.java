package com.lotfresh.productservice.domain.category.api;

import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.request.CategoryModifyRequest;
import com.lotfresh.productservice.domain.category.service.CategoryService;
import com.lotfresh.productservice.domain.category.service.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PutMapping("/{categoryId}")
  public ResponseEntity<Void> modifyCategory(
      @Valid @RequestBody CategoryModifyRequest request,
      @PathVariable("categoryId") Long categoryId) {
    categoryService.modifyCategory(request, categoryId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{categoryId}")
  public ResponseEntity<Void> softDeleteCategory(@PathVariable("categoryId") Long categoryId) {
    categoryService.softDeleteCategory(categoryId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable("categoryId") Long categoryId) {
    return ResponseEntity.ok(categoryService.getCategory(categoryId));
  }
}

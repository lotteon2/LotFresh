package com.lotfresh.productservice.domain.discount.api;

import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.api.request.DiscountModifyRequest;
import com.lotfresh.productservice.domain.discount.service.DiscountService;
import com.lotfresh.productservice.domain.discount.service.response.DiscountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discounts")
public class DiscountApiController {
  private final DiscountService discountService;

  @PostMapping("")
  public ResponseEntity<Long> createDiscount(@Valid @RequestBody DiscountCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(discountService.createDiscount(request));
  }

  @PutMapping("/{discountId}")
  public ResponseEntity<Void> modifyDiscount(
      @Valid @RequestBody DiscountModifyRequest request,
      @PathVariable("discountId") Long discountId) {
    discountService.modifyDiscount(request, discountId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{discountId}")
  public ResponseEntity<DiscountResponse> getDiscount(@PathVariable("discountId") Long discountId) {
    return ResponseEntity.ok(discountService.getDiscount(discountId));
  }

  @GetMapping("")
  public ResponseEntity<List<DiscountResponse>> getDiscounts() {
    return ResponseEntity.ok(discountService.getDiscounts());
  }
}

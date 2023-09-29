package com.lotfresh.productservice.domain.discount.api;

import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.service.DiscountService;
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
@RequestMapping("/discounts")
public class DiscountApiController {
    private final DiscountService discountService;

    @PostMapping("")
    public ResponseEntity<Long> createDiscount(@Valid @RequestBody DiscountCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(discountService.createDiscount(request));
    }
}

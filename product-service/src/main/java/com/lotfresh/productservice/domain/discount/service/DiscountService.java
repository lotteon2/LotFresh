package com.lotfresh.productservice.domain.discount.service;

import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
}

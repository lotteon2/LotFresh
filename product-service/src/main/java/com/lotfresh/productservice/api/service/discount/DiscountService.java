package com.lotfresh.productservice.api.service.discount;

import com.lotfresh.productservice.domain.discount.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
}

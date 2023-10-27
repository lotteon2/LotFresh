package com.lotfresh.productservice.domain.discount.repository;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.repository.custom.DiscountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository
    extends JpaRepository<Discount, Long>, DiscountRepositoryCustom {}

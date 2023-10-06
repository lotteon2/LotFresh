package com.lotfresh.productservice.domain.discount.repository;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.repository.custom.DiscountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DiscountRepository
    extends JpaRepository<Discount, Long>, DiscountRepositoryCustom {

  @Modifying(clearAutomatically = true)
  @Query(
      value =
          "update discount set is_deleted = true where is_deleted = false and end_date = curdate() +interval 1 day ", nativeQuery = true)
  void updateExpiredDiscounts();
}

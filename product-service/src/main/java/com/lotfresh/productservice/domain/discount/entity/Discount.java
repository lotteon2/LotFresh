package com.lotfresh.productservice.domain.discount.entity;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "category_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_discount_to_category"))
  private Category category;

  @Column(nullable = false)
  private Integer rate;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private LocalDate endDate;

  @Column(nullable = false)
  private String imgurl;

  @Builder
  private Discount(
      Category category, Integer rate, LocalDate startDate, LocalDate endDate, String imgurl) {
    this.category = category;
    this.rate = rate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.imgurl = imgurl;
  }
}

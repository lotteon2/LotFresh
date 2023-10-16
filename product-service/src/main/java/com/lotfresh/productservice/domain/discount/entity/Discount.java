package com.lotfresh.productservice.domain.discount.entity;

import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_discount_to_category"))
  private Category category;

  @NotNull private Double rate;

  @NotNull private LocalDate startDate;

  @NotNull private LocalDate endDate;

  @NotNull private String imgurl;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean isDeleted;

  @Builder
  private Discount(
      Category category, Double rate, LocalDate startDate, LocalDate endDate, String imgurl) {
    this.category = category;
    this.rate = rate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.imgurl = imgurl;
  }

  public void changeDiscount(Double rate, String imgUrl) {
    this.rate = rate;
    this.imgurl = imgUrl;
  }
}

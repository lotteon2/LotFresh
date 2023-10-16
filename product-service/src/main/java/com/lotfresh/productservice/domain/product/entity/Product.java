package com.lotfresh.productservice.domain.product.entity;

import com.lotfresh.productservice.common.BaseEntity;
import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_to_category"))
  private Category category;

  @NotNull private String name;

  @NotNull private String thumbnail;

  @NotNull private String detail;

  @NotNull private Integer price;

  @NotNull
  @Column(unique = true)
  private String productCode;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean isDeleted;

  @Builder
  private Product(
      Category category,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      String productCode) {
    this.category = category;
    this.name = name;
    this.thumbnail = thumbnail;
    this.detail = detail;
    this.price = price;
    this.productCode = productCode;
  }

  public void changeProduct(
      Category category,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      String productCode) {
    this.category = category;
    this.name = name;
    this.thumbnail = thumbnail;
    this.detail = detail;
    this.price = price;
    this.productCode = productCode;
  }

  public void softDelete() {
    this.isDeleted = true;
  }
}

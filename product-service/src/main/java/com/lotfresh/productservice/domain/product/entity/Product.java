package com.lotfresh.productservice.domain.product.entity;

import com.lotfresh.productservice.common.BaseEntity;
import com.lotfresh.productservice.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "category_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_product_to_category"))
  private Category category;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String thumbnail;

  @Column(nullable = false)
  private String detail;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private String productCode;

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
}

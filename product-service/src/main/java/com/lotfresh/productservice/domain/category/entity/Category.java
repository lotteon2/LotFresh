package com.lotfresh.productservice.domain.category.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_category_to_parent_category"))
  private Category parent;

  @Column(nullable = true, columnDefinition = "boolean default false")
  private Boolean isDeleted = false;

  @OneToMany(mappedBy = "parent")
  List<Category> children = new ArrayList<>();

  @Builder
  private Category(String name, Category parent) {
    this.name = name;
    this.parent = parent;
  }

  public void changeCategory(Category parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public void softDelete() {
    this.isDeleted = true;
  }
}

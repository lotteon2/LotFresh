package com.lotfresh.userservice.domain.member.entity;

import com.lotfresh.userservice.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String name;

  private String gender;

  @Builder
  private Member(String email, String name, String gender) {
    this.email = email;
    this.name = name;
    this.gender = gender;
  }
}

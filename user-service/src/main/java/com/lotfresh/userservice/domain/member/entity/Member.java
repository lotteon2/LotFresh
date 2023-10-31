package com.lotfresh.userservice.domain.member.entity;

import com.lotfresh.userservice.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column private String nickname;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean isActive;

  @Builder
  private Member(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }
}

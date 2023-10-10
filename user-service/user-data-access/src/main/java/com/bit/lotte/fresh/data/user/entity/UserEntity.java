package com.bit.lotte.fresh.data.user.entity;

import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private Gender gender;
  @Column(nullable = false)
  private String contact;
  @OneToMany(cascade = javax.persistence.CascadeType.ALL)
  private List<AddressEntity> addressEntityList = new java.util.ArrayList<>();
}

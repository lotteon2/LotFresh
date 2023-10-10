package com.bit.lotte.fresh.data.user.entity;

import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.valueobject.Province;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
@Entity
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Province province;
  @Column(nullable = false)
  private String roadAddress;
  @Column(nullable = false)
  private String detailAddress;
  @Column(nullable = false)
  private String zipCode;
  @Column(nullable = false)
  private boolean defaultAddress;
  @ManyToOne(optional = false)
  private UserEntity user;

  public boolean getDefaultAddress(){
    return this.defaultAddress;
  }
}

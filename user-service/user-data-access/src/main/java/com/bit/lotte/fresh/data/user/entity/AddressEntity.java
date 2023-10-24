package com.bit.lotte.fresh.data.user.entity;

import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.valueobject.Province;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "address")
@Entity
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long id;
  @Column(nullable = false, name = "province")
  private Province province;
  @Column(nullable = false, name = "roadAddress")
  private String roadAddress;
  @Column(nullable = false, name = "detailAddress")
  private String detailAddress;
  @Column(nullable = false, name = "zipCode")
  private String zipCode;
  @Column(nullable = false, name = "defaultAddress")
  private boolean defaultAddress;
  @JoinColumn(name = "user_id")
  @ManyToOne
  private UserEntity user;

  public boolean getDefaultAddress() {
    return this.defaultAddress;
  }
}

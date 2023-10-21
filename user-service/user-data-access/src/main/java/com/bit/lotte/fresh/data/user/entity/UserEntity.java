package com.bit.lotte.fresh.data.user.entity;

import com.bit.lotte.fresh.domain.valueobject.Gender;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
@Getter
public class UserEntity {

  @Id
  @Column(name = "user_id")
  private Long id;
  @Column(nullable = false, name = "name")
  private String name;
  @Column(nullable = false, name = "gender")
  private Gender gender;
  @Column(nullable = false, name = "contact")
  private String contact;
@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
  private List<AddressEntity> addressList = new ArrayList<>();


}

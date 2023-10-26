package com.bit.lotte.fresh.service.dto.response;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataResponse {

  @NotNull
  private UserId userId;
  @NotNull
  private  String name;
  @NotNull
  private  String contact;
  @NotNull
  private  Gender gender;

  public UserDataResponse(User user) {
    this.userId = user.getEntityId();
    this.name = user.getName();
    this.contact = user.getContact();
    this.gender = user.getGender();
  }


}

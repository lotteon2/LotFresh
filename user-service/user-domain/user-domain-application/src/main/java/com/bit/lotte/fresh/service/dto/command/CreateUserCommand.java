package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {

  @NotNull
  private UserId userId;
  @NotNull
  private String name;
  @NotNull
  private AuthProvider authProvider;
  @NotNull
  private String contact;
  @NotNull Province province;
  @NotNull String detailAddress;
  @NotNull String zipcode;




}

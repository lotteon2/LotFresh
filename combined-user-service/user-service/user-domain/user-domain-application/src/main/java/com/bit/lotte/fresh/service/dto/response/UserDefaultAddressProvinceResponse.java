package com.bit.lotte.fresh.service.dto.response;


import com.bit.lotte.fresh.domain.valueobject.Province;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDefaultAddressProvinceResponse {

  @NotNull
  private UserId userId;
  @NotNull
  private Province defaultAddressProvince;
}

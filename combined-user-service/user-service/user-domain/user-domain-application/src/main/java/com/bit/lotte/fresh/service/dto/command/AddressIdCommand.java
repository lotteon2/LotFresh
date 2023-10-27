package com.bit.lotte.fresh.service.dto.command;

import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressIdCommand {
  private AddressId addressId;
}

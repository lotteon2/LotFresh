package com.bit.lotte.fresh.cart.service.dto.command;

import com.bit.lotte.fresh.cart.common.domain.valueobject.ProductId;
import com.bit.lotte.fresh.cart.common.domain.valueobject.UserCartId;
import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateCartSelectedQuantityCommand {

  @NotNull
  private CartItemIdCommand cartItemIdCommand;
  @NotNull
  private int increasedQuantity;


}

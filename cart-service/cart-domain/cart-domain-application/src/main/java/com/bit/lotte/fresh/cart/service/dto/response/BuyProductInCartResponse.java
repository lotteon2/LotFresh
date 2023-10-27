package com.bit.lotte.fresh.cart.service.dto.response;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BuyProductInCartResponse {

  @NotNull
  private CartItemInfoResponse cartItemInfoResponse;
  private String message;

  public BuyProductInCartResponse(
      CartItemInfoResponse cartItemInfoResponse) {
    this.cartItemInfoResponse = cartItemInfoResponse;
    setInitMessage();
  }

  private void setInitMessage() {
    StringBuilder sb = new StringBuilder();
      sb.append(cartItemInfoResponse);
    sb.append("를 구매합니다.");
    message = sb.toString();
  }


}

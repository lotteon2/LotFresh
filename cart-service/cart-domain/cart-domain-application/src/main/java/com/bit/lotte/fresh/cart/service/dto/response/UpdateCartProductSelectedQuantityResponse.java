package com.bit.lotte.fresh.cart.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCartProductSelectedQuantityResponse
{

  private CartItemInfoResponse cartItemInfoResponse;
  private int updatedSelectedQuantityNumber;
  private String message;

  public UpdateCartProductSelectedQuantityResponse(
      CartItemInfoResponse cartItemInfoResponse, int updatedSelectedQuantityNumber) {
    this.cartItemInfoResponse = cartItemInfoResponse;
    this.updatedSelectedQuantityNumber = updatedSelectedQuantityNumber;
    setInitMessage();
  }

  private void setInitMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(cartItemInfoResponse);
    sb.append("가 ");
    sb.append(updatedSelectedQuantityNumber);
    sb.append("로 업데이트 됩니다.");
    message = sb.toString();
  }
}

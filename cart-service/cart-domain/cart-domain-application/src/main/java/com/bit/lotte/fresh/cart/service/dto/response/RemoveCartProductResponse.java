package com.bit.lotte.fresh.cart.service.dto.response;

import java.util.List;
import javax.validation.constraints.NotNull;

public class RemoveCartProductResponse {

  @NotNull
  private CartItemInfoResponse response;
  private String message;

  public RemoveCartProductResponse(
      CartItemInfoResponse response) {
    this.response = response;
    setInitMessage();
  }


  private void setInitMessage() {
    StringBuilder sb = new StringBuilder();
      sb.append(response);
    sb.append("가 삭제됩니다.");
    message = sb.toString();
  }
}

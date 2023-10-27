package com.bit.lotte.fresh.cart.service.dto.response;

import com.bit.lotte.fresh.cart.domain.entity.Cart;
import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProductInCartResponse {

  private Cart cart;
  private CartItem cartItem;
  private String message;

  public AddProductInCartResponse(Cart cart, CartItem cartItem) {
    this.cart = cart;
    this.cartItem = cartItem;
    setInitMessage();
  }

  private void setInitMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(cart.getEntityId());
    sb.append("에");
    sb.append(cartItem);
    sb.append("가 추가되었습니다.");
  }

}

package com.bit.lotte.fresh.cart.service.mapper;

import com.bit.lotte.fresh.cart.common.domain.valueobject.ProductId;
import com.bit.lotte.fresh.cart.common.domain.valueobject.UserCartId;
import com.bit.lotte.fresh.cart.domain.event.cart.AddCarItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.BuyCartItemDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.RemoveCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.UpdateSelectedQuantityCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import com.bit.lotte.fresh.cart.service.dto.command.CartItemIdCommand;
import com.bit.lotte.fresh.cart.service.dto.response.AddProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.BuyProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.CartItemInfoResponse;
import com.bit.lotte.fresh.cart.service.dto.response.RemoveCartProductResponse;
import com.bit.lotte.fresh.cart.service.dto.response.UpdateCartProductSelectedQuantityResponse;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

  public AddProductInCartResponse AddCartToResponse(AddCarItemCartDomainEvent event) {
    return new AddProductInCartResponse(event.getCart(), event.getCartItem());
  }

  public CartItemIdCommand cartItemIdCommandCreator(UserCartId cartId, Province province,
      ProductId productId, int selectedQuantity) {
    return new CartItemIdCommand(cartId, productId, province);
  }


  public BuyProductInCartResponse buyCartProductEventToResponse(
      BuyCartItemDomainEvent event) {
    return new BuyProductInCartResponse(new CartItemInfoResponse(event.getCart().getEntityId(),
        event.getCartItem()));
  }


  public RemoveCartProductResponse removeCartEventToResponse(
      RemoveCartItemCartDomainEvent event) {

    return new RemoveCartProductResponse(new CartItemInfoResponse(event.getCart().getEntityId(),
        event.getCartItem()));
  }



  public UpdateCartProductSelectedQuantityResponse selectedQuantityUpdateEventToResponse(
      UpdateSelectedQuantityCartItemCartDomainEvent event) {
    CartItemInfoResponse responseDto = new CartItemInfoResponse(event.getCart().getEntityId(),
        event.getCartItem());
    return new UpdateCartProductSelectedQuantityResponse(responseDto,
        event.getUpdatedSelectedQuantity());
  }
}

package com.bit.lotte.fresh.cart.service.port.input;

import com.bit.lotte.fresh.cart.service.dto.command.AddProductInCartCommand;
import com.bit.lotte.fresh.cart.service.dto.command.CartItemIdCommand;
import com.bit.lotte.fresh.cart.service.dto.command.GetMyAllCartItemCommand;
import com.bit.lotte.fresh.cart.service.dto.command.UpdateCartSelectedQuantityCommand;
import com.bit.lotte.fresh.cart.service.dto.response.AddProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.BuyProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.GetMyAllCartItemListResponse;
import com.bit.lotte.fresh.cart.service.dto.response.RemoveCartProductResponse;
import com.bit.lotte.fresh.cart.service.dto.response.UpdateCartProductSelectedQuantityResponse;


public interface CartApplicationService {

  GetMyAllCartItemListResponse getCartItemList(GetMyAllCartItemCommand command);
  AddProductInCartResponse addProductInCart(AddProductInCartCommand command);
  BuyProductInCartResponse buyProductListInCart(CartItemIdCommand command);
  RemoveCartProductResponse removeCartProduct(CartItemIdCommand command);
  UpdateCartProductSelectedQuantityResponse updateCartProductSelectedQuantity(
      UpdateCartSelectedQuantityCommand command);
}

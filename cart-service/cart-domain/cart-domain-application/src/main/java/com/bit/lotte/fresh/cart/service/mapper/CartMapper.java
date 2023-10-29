package com.bit.lotte.fresh.cart.service.mapper;

import com.bit.lotte.fresh.cart.common.domain.valueobject.CartItemCompKey;
import com.bit.lotte.fresh.cart.common.domain.valueobject.ProductId;
import com.bit.lotte.fresh.cart.common.domain.valueobject.UserCartId;
import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import com.bit.lotte.fresh.cart.domain.entity.Product;
import com.bit.lotte.fresh.cart.domain.event.cart.AddCarItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.BuyCartItemDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.RemoveCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.UpdateSelectedQuantityCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import com.bit.lotte.fresh.cart.service.dto.command.AddProductInCartCommand;
import com.bit.lotte.fresh.cart.service.dto.command.CartItemIdCommand;
import com.bit.lotte.fresh.cart.service.dto.response.AddProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.BuyProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.CartItemInfoResponse;
import com.bit.lotte.fresh.cart.service.dto.response.GetMyAllCartItemListResponse;
import com.bit.lotte.fresh.cart.service.dto.response.RemoveCartProductResponse;
import com.bit.lotte.fresh.cart.service.dto.response.UpdateCartProductSelectedQuantityResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

  public AddProductInCartResponse AddCartToResponse(AddCarItemCartDomainEvent event) {
    Product product = event.getCartItem().getProduct();
    return new AddProductInCartResponse(product.getEntityId(), product.getProvince(),
        product.getName());
  }

  public CartItemIdCommand cartItemIdCommandCreator(UserCartId cartId, Province province,
      ProductId productId, int selectedQuantity) {
    return new CartItemIdCommand(cartId, productId, province);
  }


  public BuyProductInCartResponse buyCartProductEventToResponse(
      BuyCartItemDomainEvent event) {
    int selectedQuantity = event.getCartItem().getSelectedQuantity();
    Product product = event.getCartItem().getProduct();
    return new BuyProductInCartResponse(product.getEntityId(), product.getProvince(),
        product.getName(), selectedQuantity);
  }


  public RemoveCartProductResponse removeCartEventToResponse(
      RemoveCartItemCartDomainEvent event) {
    Product product = event.getCartItem().getProduct();
    return new RemoveCartProductResponse(product.getEntityId(), product.getProvince(),
        product.getName());
  }


  public UpdateCartProductSelectedQuantityResponse selectedQuantityUpdateEventToResponse(
      UpdateSelectedQuantityCartItemCartDomainEvent event) {
    int updatedSelectedQuantity = event.getUpdatedSelectedQuantity();
    Product product = event.getCartItem().getProduct();
    return new UpdateCartProductSelectedQuantityResponse(product.getProvince(), product.getName(),
        updatedSelectedQuantity);
  }

  //여기서 부터 수정
  public List<GetMyAllCartItemListResponse> cartItemListToMyCartResponse(List<CartItem> cartItems) {
    List<GetMyAllCartItemListResponse> list = new ArrayList<>();
    for (CartItem cartItem : cartItems) {
      Product product = cartItem.getProduct();
      list.add(
          GetMyAllCartItemListResponse.builder().name(product.getName()).price(product.getPrice())
              .province(product.getProvince()).discountedPrice(product.getDiscountedPrice())
              .productId(product.getEntityId().getValue()).build());
    }
    return list;
  }

  public CartItem getCartItemFromAddProductInCartCommand(UserCartId userCartId, AddProductInCartCommand command) {
   return CartItem.builder().product(
            Product.builder().productStock(command.getProductStock())
                .description(command.getProductImageUrl()).discountedPrice(command.getDiscountedPrice())
                .productStock(command.getProductStock()).id(command.getProductId()).build())
        .id(new CartItemCompKey(
            command.getProductId(), userCartId)).selectedQuantity(command.getSelectedQuantity())
        .build();
  }
}

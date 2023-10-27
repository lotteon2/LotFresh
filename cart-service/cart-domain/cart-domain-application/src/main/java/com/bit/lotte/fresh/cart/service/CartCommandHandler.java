package com.bit.lotte.fresh.cart.service;

import com.bit.lotte.fresh.cart.common.domain.valueobject.CartItemCompKey;
import com.bit.lotte.fresh.cart.common.domain.valueobject.UserCartId;
import com.bit.lotte.fresh.cart.domain.CartDomainService;
import com.bit.lotte.fresh.cart.domain.CartDomainServiceImpl;
import com.bit.lotte.fresh.cart.domain.entity.Cart;
import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import com.bit.lotte.fresh.cart.domain.event.cart.AddCarItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.BuyCartItemDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.GetMyCartItemEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.RemoveCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.event.cart.UpdateSelectedQuantityCartItemCartDomainEvent;
import com.bit.lotte.fresh.cart.domain.excepton.CartDomainException;
import com.bit.lotte.fresh.cart.service.dto.command.AddProductInCartCommand;
import com.bit.lotte.fresh.cart.service.dto.command.CartItemIdCommand;
import com.bit.lotte.fresh.cart.service.dto.command.GetMyAllCartItemCommand;
import com.bit.lotte.fresh.cart.service.repository.CartRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartCommandHandler {

  private final CartDomainService cartDomainService = new CartDomainServiceImpl();
  private final CartRepository cartRepository;

  private Cart findCart(UserCartId cartId) {
    return cartRepository.findCart(cartId);
  }

  private CartItem findCartItem(CartItemIdCommand command) {
    return cartRepository.findCartItem(
        new CartItemCompKey(command.getProductId(), command.getUserCartId()),
        command.getProvince());
  }

  private Cart cartInitHelper(AddProductInCartCommand command) {
    Cart foundCart =  findCart(command.getUserCartId());
    if (foundCart==null) {
      return Cart.builder().id(command.getUserCartId()).cartItemList(null).build();
    }
    return foundCart;
  }

  private void cartItemExistChecker(CartItemIdCommand command) {
    CartItem foundCartItem = findCartItem(command);

    if (foundCartItem==null) {
      throw new CartDomainException("존재하지 않는 카트 상품입니다.");
    }
  }


  @Transactional
  public AddCarItemCartDomainEvent addCartItem(AddProductInCartCommand command) {
    Cart initCart = cartInitHelper(command);
    CartItem cartItem = command.getCartItem();

    AddCarItemCartDomainEvent event = cartDomainService.addProductInCart(initCart, cartItem);

    CartItem savedCartItem = cartRepository.addCartItem(event.getCart().getEntityId(),event.getCartItem());
    if (savedCartItem != null) {
      return event;
    }
    throw new CartDomainException("상품을 추가할 수 없습니다.");
  }


  @Transactional
  public BuyCartItemDomainEvent buyProduct(CartItemIdCommand command) {
    cartItemExistChecker(command);
    Cart cart = findCart(command.getUserCartId());
    CartItem cartItem = findCartItem(command);

    BuyCartItemDomainEvent event = cartDomainService.buyCartItem(cart, cartItem);

    cartRepository.removeCartItem(cartItem);

    return event;
  }

  @Transactional
  public RemoveCartItemCartDomainEvent removeCartItem(CartItemIdCommand command) {

    cartItemExistChecker(command);
    Cart cart = findCart(command.getUserCartId());
    CartItem cartItem = findCartItem(command);

    RemoveCartItemCartDomainEvent event = cartDomainService.removeCartItem(cart, cartItem);

    cartRepository.removeCartItem(event.getCartItem());


    return event;
  }


  @Transactional
  public UpdateSelectedQuantityCartItemCartDomainEvent updateCartProductSelectedQuantity(
      CartItemIdCommand command, int updatedQuantity) {

    cartItemExistChecker(command);
    Cart cart = findCart(command.getUserCartId());
    CartItem cartItem = findCartItem(command);

    UpdateSelectedQuantityCartItemCartDomainEvent event = cartDomainService.updateSelectedProductQuantity(
        cart, cartItem, updatedQuantity);
    CartItem updatedCartItem = cartRepository.updateSelectedQuantity(event.getCartItem());
    if (updatedCartItem != null) {
      return event;
    }
    throw new CartDomainException("선택한 개수만큼 업데이트를 반영할 수 없습니다.");
  }

  public GetMyCartItemEvent getMyCartItem(GetMyAllCartItemCommand command, CartItem cartItem) {
    Cart cart = findCart(command.getUserCartId());
    if (cart == null) {
      throw new CartDomainException("존재 하지 않는 카트 상품입니다.");
    } else {
      return new GetMyCartItemEvent(cart, cartItem, ZonedDateTime.now());
    }

  }
    public List<GetMyCartItemEvent> getMyAllCartItem(GetMyAllCartItemCommand command){

      Cart cart = findCart(command.getUserCartId());
      if (cart == null) {
        return null;
      } else {
        List<GetMyCartItemEvent> eventList = new ArrayList<>();
        for(CartItem cartItem: cart.getCartItemList()){
          eventList.add(new GetMyCartItemEvent(cart,cartItem,ZonedDateTime.now()));
        }
        return eventList;
      }


    }
  }

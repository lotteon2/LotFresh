package com.bit.lotte.fresh.cart.app.rest;


import com.bit.lotte.fresh.cart.common.domain.valueobject.ProductId;
import com.bit.lotte.fresh.cart.common.domain.valueobject.UserCartId;
import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import com.bit.lotte.fresh.cart.service.dto.command.AddProductInCartCommand;
import com.bit.lotte.fresh.cart.service.dto.command.CartItemIdCommand;
import com.bit.lotte.fresh.cart.service.dto.command.GetMyAllCartItemCommand;
import com.bit.lotte.fresh.cart.service.dto.command.UpdateCartSelectedQuantityCommand;
import com.bit.lotte.fresh.cart.service.dto.response.AddProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.BuyProductInCartResponse;
import com.bit.lotte.fresh.cart.service.dto.response.GetMyAllCartItemListResponse;
import com.bit.lotte.fresh.cart.service.dto.response.RemoveCartProductResponse;
import com.bit.lotte.fresh.cart.service.dto.response.UpdateCartProductSelectedQuantityResponse;
import com.bit.lotte.fresh.cart.service.port.input.CartApplicationService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartRestController {

  private final CartApplicationService applicationService;

  @GetMapping("/carts")
  public ResponseEntity<List<GetMyAllCartItemListResponse>> getMyAllCartItemList(
      @RequestHeader Long userId) {
    List<GetMyAllCartItemListResponse> response = applicationService.getCartItemList(
        new GetMyAllCartItemCommand(new UserCartId(userId)));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/carts")
  public ResponseEntity<AddProductInCartResponse> addItemIntoCart(
      @Valid @RequestBody AddProductInCartCommand command,@RequestHeader Long userId) {
    log.info("userId:" + userId);
    log.info("product name:" + command.getProductName());
    AddProductInCartResponse response = applicationService.addProductInCart(new UserCartId(userId),command);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/carts/province/{province}/products/{productId}")
  public ResponseEntity<RemoveCartProductResponse> removeCartItem(
      @PathVariable Province province,
      @RequestHeader Long userId, @PathVariable Long productId) {
    return ResponseEntity.ok(
        applicationService.removeCartProduct(
            new CartItemIdCommand(new UserCartId(userId), new ProductId(productId), province)));
  }

  @PutMapping("/carts/province/{province}/products/{productId}/stock/{selectedQuantity}")
  public ResponseEntity<UpdateCartProductSelectedQuantityResponse> updateSelectedQuantity(
      @RequestHeader Long userId, @PathVariable Long productId,
      @PathVariable Province province,@PathVariable Integer selectedQuantity) {
    return ResponseEntity.ok(applicationService.updateCartProductSelectedQuantity(
        new UpdateCartSelectedQuantityCommand(new CartItemIdCommand(new UserCartId(userId),new ProductId(productId),province),selectedQuantity)));
  }

  @PostMapping("/carts/province/{province}/product/{productId}")
  public ResponseEntity<BuyProductInCartResponse> buyProductInCart(
      @RequestHeader Long userId, @PathVariable Province province,
      @PathVariable Long productId) {
    return ResponseEntity.ok(applicationService.buyProductListInCart(
        new CartItemIdCommand(new UserCartId(userId), new ProductId(productId), province)));
  }


}

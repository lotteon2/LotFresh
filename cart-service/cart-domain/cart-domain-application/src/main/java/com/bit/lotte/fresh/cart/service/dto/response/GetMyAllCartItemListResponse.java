package com.bit.lotte.fresh.cart.service.dto.response;

import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyAllCartItemListResponse {
  public List<CartItem> cartItemList;
}

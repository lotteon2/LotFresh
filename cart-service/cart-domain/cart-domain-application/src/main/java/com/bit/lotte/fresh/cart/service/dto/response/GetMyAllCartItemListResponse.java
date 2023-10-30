package com.bit.lotte.fresh.cart.service.dto.response;

import com.bit.lotte.fresh.cart.domain.entity.CartItem;
import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetMyAllCartItemListResponse {
   private int selectedQuantity;
      private Long productId;
      private long discountedPrice;
      private  long price;
      private Province province;
      private  long productStock;
      private String name;
      private String productImageUrl;
}

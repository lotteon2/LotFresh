package com.bit.lotte.fresh.cart.service.dto.response;

import com.bit.lotte.fresh.cart.domain.valueobject.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCartProductSelectedQuantityResponse
{

  private Province province;
  private String productName;
  private int updatedSelectedQuantityNumber;
  private String message;

  public UpdateCartProductSelectedQuantityResponse(
      Province province, String productName, int updatedSelectedQuantityNumber) {
    this.province = province;
    this.productName = productName;
    this.updatedSelectedQuantityNumber = updatedSelectedQuantityNumber;
    setInitMessage();
  }

  private void setInitMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(province);
    sb.append("의 ");
    sb.append(productName);
    sb.append("가 ");
    sb.append(updatedSelectedQuantityNumber);
    sb.append("로 업데이트 됩니다.");
    message = sb.toString();
  }
}

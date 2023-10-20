package com.bit.lotte.fresh.message.publish;

import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;
import com.bit.lotte.fresh.auth.port.output.SubCategoryAdminIdListPublisher;
import com.bit.lotte.fresh.feign.ProductFeignClient;


public class SubCategoryAdminIdListPublisherImpl implements SubCategoryAdminIdListPublisher  {

  private final ProductFeignClient productFeignClient;

  public SubCategoryAdminIdListPublisherImpl(
      ProductFeignClient productFeignClient) {
    this.productFeignClient = productFeignClient;
  }

  @Override
  public void publish(UpdateUserAuthRoleDomainEvent event) {

  }
}

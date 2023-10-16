package com.bit.lotte.fresh.message.publish;

import com.bit.lotte.fresh.cloud.feign.CreateUserResponseFeign;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.service.port.output.CreateUserEventPublisher;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserCreatedEventPublisherImpl implements CreateUserEventPublisher{

  private final CreateUserResponseFeign userFeignClient;

  @Override
  public void publish(CreateUserDomainEvent createUserDomainEvent) {
    Long value = createUserDomainEvent.getUser().getId().getValue();
    userFeignClient.responseToAuthService(new AuthUserId(value));
  }

}

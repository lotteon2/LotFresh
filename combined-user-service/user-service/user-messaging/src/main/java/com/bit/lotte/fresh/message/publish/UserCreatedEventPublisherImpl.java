package com.bit.lotte.fresh.message.publish;

import com.bit.lotte.fresh.cloud.feign.CreateUserResponseFeign;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.service.port.output.CreateUserEventPublisher;
import com.bit.lotte.fresh.user.common.message.CreateAuthUserCommand;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserCreatedEventPublisherImpl implements CreateUserEventPublisher{

  private final CreateUserResponseFeign userFeignClient;

  @Override
  public void publish(CreateUserDomainEvent createUserDomainEvent, AuthProvider authProvider) {
    Long value = createUserDomainEvent.getUser().getEntityId().getValue();
    userFeignClient.responseToAuthService(
        new CreateAuthUserCommand(new AuthUserId(value), authProvider));
  }

}

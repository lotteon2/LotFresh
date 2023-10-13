package com.bit.lotte.fresh.message.publish;

import com.bit.lotte.fresh.cloud.feign.CreateUserResponseFeign;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.service.port.output.CreateUserEventPublisher;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Component
public class UserCreatedEventPublisherImpl implements CreateUserEventPublisher{

  private final CreateUserResponseFeign userFeignClient;

  @Override
  public void publish(CreateUserDomainEvent createUserDomainEvent) {
    Long value = createUserDomainEvent.getUser().getId().getValue();
    responseToCreationUserToAuth(new AuthUserId(value));
  }
  @PostMapping("/")
   public ResponseEntity<Object> responseToCreationUserToAuth(AuthUserId authId){
        return ResponseEntity.ok().body(userFeignClient.responseToAuthService(authId));
    }
}

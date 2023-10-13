package com.bit.lotte.fresh.auth.port.output;

import com.bit.lotte.fresh.auth.event.CreateAuthDomainEvent;
import org.springframework.stereotype.Component;

@Component
public interface CreateAuthUserEventPublisher {
  void publish(CreateAuthDomainEvent createAuthDomainEvent);
}

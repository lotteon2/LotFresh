package com.bit.lotte.fresh.service.port.output;

import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import org.springframework.stereotype.Service;

@Service
public interface CreateUserEventPublisher {
  void publish(CreateUserDomainEvent createUserDomainEvent);
}

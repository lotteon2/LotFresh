package com.bit.lotte.fresh.service.port.output;

import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.AuthProvider;

public interface CreateUserEventPublisher {
  void publish(CreateUserDomainEvent createUserDomainEvent, AuthProvider authProvider);
}

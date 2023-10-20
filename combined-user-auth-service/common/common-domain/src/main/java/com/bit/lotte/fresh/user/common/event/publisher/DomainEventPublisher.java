package com.bit.lotte.fresh.user.common.event.publisher;

import com.bit.lotte.fresh.user.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<T>> {

    void publish(T domainEvent);
}

package com.bit.lotte.fresh.auth.service.port.output;

import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;

public interface SubCategoryAdminIdListPublisher {
  void publish(UpdateUserAuthRoleDomainEvent event);
}

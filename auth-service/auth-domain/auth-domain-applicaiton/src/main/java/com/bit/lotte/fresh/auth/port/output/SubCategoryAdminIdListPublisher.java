package com.bit.lotte.fresh.auth.port.output;

import com.bit.lotte.fresh.auth.event.UpdateUserAuthRoleDomainEvent;

public interface SubCategoryAdminIdListPublisher {
  void listen(UpdateUserAuthRoleDomainEvent event);
}

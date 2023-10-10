package com.bit.lotte.fresh.service.repository;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.Optional;

public interface UserRepository {

  User get(UserId id);

  User save(User user);

  User update(User user);

  void delete(UserId id);


}
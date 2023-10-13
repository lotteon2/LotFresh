package com.bit.lotte.fresh.dataaccess.repository;

import com.bit.lotte.fresh.auth.entity.AuthUser;
import com.bit.lotte.fresh.user.common.valueobject.AuthUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserJpaRepository extends JpaRepository<AuthUser, AuthUserId> {

}

package com.bit.lotte.fresh.dataaccess.repository;

import com.bit.lotte.fresh.dataaccess.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserEntityJpaRepository extends JpaRepository<AuthUserEntity, Long> {

}

package com.bit.lotte.fresh.data.user.repository;

import com.bit.lotte.fresh.data.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);

}

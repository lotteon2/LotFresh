package com.bit.lotte.fresh.data.user.adapter;

import com.bit.lotte.fresh.data.user.entity.UserEntity;
import com.bit.lotte.fresh.data.user.repository.UserJpaRepository;
import com.bit.lotte.fresh.data.user.mapper.UserDataAccessMapper;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.service.repository.UserRepository;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserDataAccessMapper userDataAccessMapper;
  private final UserJpaRepository userJpaRepository;

  @Override
  public User get(UserId id) {
    Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id.getValue());
    return userEntityOptional.map(userDataAccessMapper::userEntityOptionalToUser).orElseThrow(()->{
      throw new IllegalArgumentException("존재하지 않는 유저의 아이디입니다.");
    });
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = userJpaRepository.save(userDataAccessMapper.userToUserEntity(user));
    if (userEntity != null) {
      return user;
    }
    return null;
  }

  @Override
  public User update(User user) {
    UserEntity userEntity = userJpaRepository.save(userDataAccessMapper.userToUserEntity(user));
    if (userEntity != null) {
      return user;
    }
    return null;
  }


  @Override
  public void delete(UserId id) {
    userJpaRepository.deleteById(id.getValue());
  }
}

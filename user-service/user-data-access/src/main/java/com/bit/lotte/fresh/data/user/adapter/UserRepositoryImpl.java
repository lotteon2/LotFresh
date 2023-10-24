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
    return userEntityOptional.map(userDataAccessMapper::userEntityToUser).orElseThrow(()->{
      throw new IllegalArgumentException("존재하지 않는 유저의 아이디입니다.");
    });
  }

  /**
   *
   * @param user 유저가 이미 존재 하더라도 user는 save된다.
   * @return 따라서 null값을 반환해 상위 로직에서 존재한다는 유저의 알림을 보낸다
   */
  @Override
  public User save(User user) {
    if (userJpaRepository.findById(user.getEntityId().getValue()).isPresent()) {
      return null;
    }

    UserEntity userEntity = userJpaRepository.save(userDataAccessMapper.userToUserEntity(user));
    return userDataAccessMapper.userEntityToUser(userEntity);
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

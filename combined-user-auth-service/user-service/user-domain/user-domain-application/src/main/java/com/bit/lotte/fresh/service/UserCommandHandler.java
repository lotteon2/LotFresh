package com.bit.lotte.fresh.service;

import com.bit.lotte.fresh.domain.UserDomainService;
import com.bit.lotte.fresh.domain.UserDomainServiceImpl;
import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.event.address.AddUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.ChangeDefaultUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.DeleteUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.DeleteUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetAddressListInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetUserInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.UpdateUserDomainEvent;
import com.bit.lotte.fresh.domain.exception.UserDomainException;
import com.bit.lotte.fresh.service.dto.command.AddAddressCommand;
import com.bit.lotte.fresh.service.dto.command.AddressIdCommand;
import com.bit.lotte.fresh.service.dto.command.CreateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UpdateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UserIdCommand;
import com.bit.lotte.fresh.service.mapper.UserDataMapper;
import com.bit.lotte.fresh.service.repository.UserRepository;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class UserCommandHandler {

  private final UserDomainService userDomainService = new UserDomainServiceImpl();
  private final UserDataMapper userDataMapper;
  private final UserRepository userRepository;

  private User getUser(UserId userId) {
    return userRepository.get(userId);
  }


  private Address getAddress(User user, AddressIdCommand addressIdCommand) {
    return user.getAddressById(addressIdCommand.getAddressId());
  }

  public GetUserInfoDomainEvent getUser(UserIdCommand userIdCommand) {
    return userDomainService.getUser(getUser(userIdCommand.getUserId()));
  }

  public List<GetAddressListInfoDomainEvent> getAddressList(UserIdCommand userIdCommand) {
    User user = getUser(userIdCommand.getUserId());
    return userDomainService.getAddressList(user);
  }

  public GetAddressListInfoDomainEvent getAddress(UserIdCommand userIdCommand,
      AddressIdCommand addressIdCommand) {
    User user = getUser(userIdCommand.getUserId());
    return userDomainService.getAddress(user, addressIdCommand.getAddressId());
  }

  public CreateUserDomainEvent createUser(CreateUserCommand createUserCommand) {
    //create Customer
    User user = userDataMapper.createCommandUserToUser(createUserCommand);

    //create Event
    CreateUserDomainEvent createUserDomainEvent = userDomainService.createUser(user);
    //save the User

    User createdUser = userRepository.save(user);
    if (createdUser == null) {
      throw new UserDomainException("유저를 생성할 수 없습니다.");
    }
    //return the DomainEvent
    return createUserDomainEvent;
  }


  public DeleteUserDomainEvent deleteUser(UserIdCommand userIdCommand) {
    User user = getUser(userIdCommand.getUserId());
    userRepository.delete(user.getId());
    return userDomainService.deleteUser(user);

  }

  public UpdateUserDomainEvent updateUser(UpdateUserCommand updateUserCommand) {
    User user = getUser(updateUserCommand.getUserId());
    User updatedUser = userDataMapper.updateUserCommandToUser(updateUserCommand,
        user.getAddressList());
    return userDomainService.updateUser(updatedUser);
  }

  public AddUserAddressDomainEvent addAddress(UserIdCommand userId, AddAddressCommand addAddressCommand) {
    User user = getUser(userId.getUserId());
    Address newAddress = userDataMapper.addAddressCommandToAddress(addAddressCommand);
    user.addAddress(newAddress);
    User updatedUser = userRepository.update(user);
    if (updatedUser == null) {
      throw new UserDomainException("주소록 추가 실패하였습니다.");
    }
    return userDomainService.addUserAddress(updatedUser, newAddress);
  }

  public DeleteUserAddressDomainEvent deleteAddress(UserIdCommand userId, AddressIdCommand addressIdCommand) {
    User user = getUser(userId.getUserId());
    Address address = getAddress(user, addressIdCommand);
    user.deleteAddress(address);
    User updatedUser = userRepository.update(user);
    return userDomainService.deleteUserAddress(updatedUser, address);

  }

  public ChangeDefaultUserAddressDomainEvent changeDefaultAddress(UserIdCommand userId,
      AddressIdCommand addressIdCommand) {
    User user = getUser(userId.getUserId());
    Address address = getAddress(user, addressIdCommand);
    user.changeDefaultAddress(address);
    User updatedUser = userRepository.update(user);
    if (updatedUser == null) {
      throw new UserDomainException("기본 주소지 설정 변경이 실패하였습니다.");
    }
    return userDomainService.changeDefaultAddress(updatedUser, address);

  }

}


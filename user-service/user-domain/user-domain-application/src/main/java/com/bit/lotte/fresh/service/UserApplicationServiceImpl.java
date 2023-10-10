package com.bit.lotte.fresh.service;

import com.bit.lotte.fresh.domain.event.address.AddUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.ChangeDefaultUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.DeleteUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.DeleteUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetUserInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.UpdateUserDomainEvent;
import com.bit.lotte.fresh.service.dto.command.AddAddressCommand;
import com.bit.lotte.fresh.service.dto.command.AddressIdCommand;
import com.bit.lotte.fresh.service.dto.command.CreateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UpdateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UserIdCommand;
import com.bit.lotte.fresh.service.dto.response.AddUserAddressResponse;
import com.bit.lotte.fresh.service.dto.response.ChangeDefaultAddressResponse;
import com.bit.lotte.fresh.service.dto.response.CreateUserResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteAddressResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteUserResponse;
import com.bit.lotte.fresh.service.dto.response.UpdateUserResponse;
import com.bit.lotte.fresh.service.dto.response.UserDataResponse;
import com.bit.lotte.fresh.service.mapper.UserDataMapper;
import com.bit.lotte.fresh.service.port.input.UserApplicationService;

import com.bit.lotte.fresh.user.common.valueobject.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

  private final UserDataMapper userDataMapper;
  private final UserCommandHandler userCommandHandler;


  @Override
  public AddUserAddressResponse addAddress(UserId userId, AddAddressCommand addAddressCommand) {
    AddUserAddressDomainEvent addUserAddressDomainEvent = userCommandHandler.addAddress(userId,
        addAddressCommand);
    return userDataMapper.addAddressDomainEventToResponse(addUserAddressDomainEvent);
  }

  @Override
  public DeleteAddressResponse deleteAddress(UserId userId, AddressIdCommand addressIdCommand) {
    DeleteUserAddressDomainEvent deleteUserAddressDomainEvent = userCommandHandler.deleteAddress(userId,
        addressIdCommand);
    return userDataMapper.deleteAddressEventToResponse(deleteUserAddressDomainEvent);
  }

  @Override
  public ChangeDefaultAddressResponse updateDefaultAddress(UserId userId, AddressIdCommand addressIdCommand) {
    ChangeDefaultUserAddressDomainEvent changeDefaultUserAddressDomainEvent = userCommandHandler.changeDefaultAddress(userId,
        addressIdCommand);
    return userDataMapper.changeDefaultAddressEventToResponse(changeDefaultUserAddressDomainEvent);
  }

  @Override
  public CreateUserResponse createUser(CreateUserCommand createUserCommand) {
    CreateUserDomainEvent createUserDomainEvent = userCommandHandler.createUser(createUserCommand);
    return userDataMapper.createUserEventToResponse(createUserDomainEvent);
  }

  @Override
  public UpdateUserResponse updateUser(UpdateUserCommand updateUserCommand) {
    UpdateUserDomainEvent updateUserDomainEvent = userCommandHandler.updateUser(updateUserCommand);
    return userDataMapper.updateUserEventToResponse(updateUserDomainEvent);

  }

  @Override
  public DeleteUserResponse deleteUser(UserIdCommand userIdCommand) {
    DeleteUserDomainEvent deleteUserDomainEvent = userCommandHandler.deleteUser(userIdCommand);
    return userDataMapper.deleteUserEventToResponse(deleteUserDomainEvent);
  }

  @Override
  public UserDataResponse getUser(UserIdCommand userIdCommand) {
        GetUserInfoDomainEvent getUserInfoDomainEvent = userCommandHandler.getUser(userIdCommand);
    return userDataMapper.getUserInfoEventToResponse(getUserInfoDomainEvent);
  }

}

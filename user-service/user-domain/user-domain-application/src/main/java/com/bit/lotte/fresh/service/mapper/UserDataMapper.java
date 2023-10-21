package com.bit.lotte.fresh.service.mapper;


import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.event.address.AddUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.ChangeDefaultUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.DeleteUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.DeleteUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetUserInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.UpdateUserDomainEvent;
import com.bit.lotte.fresh.service.dto.command.AddAddressCommand;
import com.bit.lotte.fresh.service.dto.command.CreateUserCommand;
import com.bit.lotte.fresh.service.dto.command.UpdateUserCommand;
import com.bit.lotte.fresh.service.dto.response.AddUserAddressResponse;
import com.bit.lotte.fresh.service.dto.response.ChangeDefaultAddressResponse;
import com.bit.lotte.fresh.service.dto.response.CreateUserResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteAddressResponse;
import com.bit.lotte.fresh.service.dto.response.DeleteUserResponse;
import com.bit.lotte.fresh.service.dto.response.UpdateUserResponse;
import com.bit.lotte.fresh.service.dto.response.UserDataResponse;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserDataMapper {


  public User createCommandUserToUser(CreateUserCommand createUserCommand) {
    return User.builder().contact(createUserCommand.getContact()).id(createUserCommand.getUserId())
        .gender(createUserCommand.getGender()).name(
            createUserCommand.getName()).addressList(List.of(createUserCommand.getDefaultAddress()))
        .build();
  }

  public User updateUserCommandToUser(UpdateUserCommand updateUserCommand,
      List<Address> addressList) {
    return User.builder().addressList(addressList).name(updateUserCommand.getName())
        .gender(updateUserCommand.getGender()).contact(
            updateUserCommand.getContact()).id(updateUserCommand.getUserId()).build();
  }

  public Address addAddressCommandToAddress(AddAddressCommand addAddressCommand) {
    return Address.builder().defaultAddress(addAddressCommand.isDefaultAddress())
        .detailAddress(addAddressCommand.getDetailAddress())
        .province(addAddressCommand.getProvince()).roadAddress(
            addAddressCommand.getRoadAddress()).zipCode(addAddressCommand.getZipCode())
        .build();
  }

  public AddUserAddressResponse addAddressDomainEventToResponse(
      AddUserAddressDomainEvent addUserAddressDomainEvent) {
    return new AddUserAddressResponse(addUserAddressDomainEvent.getUser(),
        addUserAddressDomainEvent.getAddress());
  }

  public DeleteAddressResponse deleteAddressEventToResponse(
      DeleteUserAddressDomainEvent deleteUserAddressDomainEvent) {
    return new DeleteAddressResponse(deleteUserAddressDomainEvent.getUser(),
        deleteUserAddressDomainEvent.getAddress());
  }

  public ChangeDefaultAddressResponse changeDefaultAddressEventToResponse(
      ChangeDefaultUserAddressDomainEvent changeDefaultUserAddressDomainEvent) {
    return new ChangeDefaultAddressResponse(changeDefaultUserAddressDomainEvent.getUser(),
        changeDefaultUserAddressDomainEvent.getAddress());
  }

  public CreateUserResponse createUserEventToResponse(CreateUserDomainEvent createUserDomainEvent) {
    return new CreateUserResponse(createUserDomainEvent.getUser());
  }

  public UpdateUserResponse updateUserEventToResponse(UpdateUserDomainEvent updateUserDomainEvent) {
    return new UpdateUserResponse(updateUserDomainEvent.getUser());
  }

  public DeleteUserResponse deleteUserEventToResponse(DeleteUserDomainEvent deleteUserDomainEvent) {
    return new DeleteUserResponse(deleteUserDomainEvent.getUser());
  }

  public UserDataResponse getUserInfoEventToResponse(GetUserInfoDomainEvent getUserInfoDomainEvent) {

    return new UserDataResponse(getUserInfoDomainEvent.getUser());
  }
}

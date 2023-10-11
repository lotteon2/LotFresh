package com.bit.lotte.fresh.service.port.input;


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
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import org.springframework.stereotype.Service;



public interface UserApplicationService {

  AddUserAddressResponse addAddress(UserId userId, AddAddressCommand addAddressCommand);
  DeleteAddressResponse deleteAddress(UserId userId, AddressIdCommand addressIdCommand);
  ChangeDefaultAddressResponse updateDefaultAddress(UserId userId, AddressIdCommand addressIdCommand);
  CreateUserResponse createUser(CreateUserCommand createUserCommand);
  UpdateUserResponse updateUser(UpdateUserCommand updateUserCommand);
  DeleteUserResponse deleteUser(UserIdCommand userIdCommand);
  UserDataResponse getUser(UserIdCommand userIdCommand);

}

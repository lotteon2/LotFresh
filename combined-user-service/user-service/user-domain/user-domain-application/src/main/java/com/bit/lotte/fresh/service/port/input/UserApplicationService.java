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
import com.bit.lotte.fresh.service.dto.response.UserAddressListResponse;
import com.bit.lotte.fresh.service.dto.response.UserDataResponse;
import com.bit.lotte.fresh.service.dto.response.UserDefaultAddressProvinceResponse;

public interface UserApplicationService {

  UserAddressListResponse getAddressList(UserIdCommand userId);
  AddUserAddressResponse addAddress(UserIdCommand userId, AddAddressCommand addAddressCommand);

  DeleteAddressResponse deleteAddress(UserIdCommand userId, AddressIdCommand addressIdCommand);

  ChangeDefaultAddressResponse updateDefaultAddress(UserIdCommand userId,
      AddressIdCommand addressIdCommand);

  CreateUserResponse createUser(CreateUserCommand createUserCommand);

  UpdateUserResponse updateUser(UpdateUserCommand updateUserCommand);

  DeleteUserResponse deleteUser(UserIdCommand userIdCommand);

  UserDataResponse getUser(UserIdCommand userIdCommand);

  UserDefaultAddressProvinceResponse getDefaultAddressProvince(UserIdCommand userIdCommand);

}
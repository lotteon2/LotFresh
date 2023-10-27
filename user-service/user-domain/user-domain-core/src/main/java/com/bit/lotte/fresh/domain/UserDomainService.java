package com.bit.lotte.fresh.domain;


import com.bit.lotte.fresh.domain.entity.Address;

import com.bit.lotte.fresh.domain.entity.User;


import com.bit.lotte.fresh.domain.event.address.AddUserAddressDomainEvent;

import com.bit.lotte.fresh.domain.event.address.ChangeDefaultUserAddressDomainEvent;

import com.bit.lotte.fresh.domain.event.address.DeleteUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.GetUserDefaultAddressEvent;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.DeleteUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetAddressListInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetUserInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.UpdateUserDomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import java.util.List;


public interface UserDomainService {

    GetUserDefaultAddressEvent getDefaultAddressProvince(User user);

    GetUserInfoDomainEvent getUser(User user);

    CreateUserDomainEvent createUser(User user);

    DeleteUserDomainEvent deleteUser(User userId);

    UpdateUserDomainEvent updateUser(User user);

    List<GetAddressListInfoDomainEvent> getAddressList(User user);

    GetAddressListInfoDomainEvent getAddress(User user, AddressId addressId);

    AddUserAddressDomainEvent addUserAddress(User user, Address address);

    ChangeDefaultUserAddressDomainEvent changeDefaultAddress(User user,
        Address newDefaultAddress);

    DeleteUserAddressDomainEvent deleteUserAddress(User user, Address address);


}

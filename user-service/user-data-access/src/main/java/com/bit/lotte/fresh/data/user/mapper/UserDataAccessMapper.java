package com.bit.lotte.fresh.data.user.mapper;

import com.bit.lotte.fresh.data.user.entity.AddressEntity;
import com.bit.lotte.fresh.data.user.entity.UserEntity;
import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDataAccessMapper {

  public User userEntityOptionalToUser(UserEntity userEntity) {
    return User.builder().addressList(addressEntityListToAddressList(userEntity.getAddressEntityList())).name(userEntity.getName()).gender(userEntity.getGender()).contact(
        userEntity.getContact()).build();

  }
  public UserEntity userToUserEntity(User user){
     return UserEntity.builder().addressEntityList(addressListToAddressEntityList(user.getAddressList())).name(user.getName()).gender(user.getGender()).contact(
        user.getContact()).build();
  }

  public List<Address> addressEntityListToAddressList(List<AddressEntity> list){
    List<Address> addressList = new ArrayList<>();
    for(AddressEntity addressEntity: list){
      addressList.add(Address.builder().province(addressEntity.getProvince()).zipCode(addressEntity.getZipCode()).defaultAddress(addressEntity.getDefaultAddress()).detailAddress(
          addressEntity.getDetailAddress()).roadAddress(addressEntity.getRoadAddress()).build());
    }
    return addressList;
  }

  public List<AddressEntity> addressListToAddressEntityList(List<Address> list){
    List<AddressEntity> addressEntityList = new ArrayList<>();
    for(Address address: list){
      addressEntityList.add(AddressEntity.builder().province(address.getProvince()).zipCode(address.getZipCode()).defaultAddress(address.isDefaultAddress()).detailAddress(
          address.getDetailAddress()).roadAddress(address.getRoadAddress()).build());
    }
    return addressEntityList;
  }
}

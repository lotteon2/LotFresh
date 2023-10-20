package com.bit.lotte.fresh.data.user.mapper;

import com.bit.lotte.fresh.data.user.entity.AddressEntity;
import com.bit.lotte.fresh.data.user.entity.UserEntity;
import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

  public User userEntityOptionalToUser(UserEntity userEntity) {
    return User.builder().addressList(addressEntityListToAddressList(userEntity.getAddressList()))
        .name(userEntity.getName()).gender(userEntity.getGender()).contact(
            userEntity.getContact()).build();

  }

  public UserEntity userToUserEntity(User user) {

    UserEntity userEntity = UserEntity.builder().id(user.getId().getValue())
        .addressList(addressListToAddressEntityList(user.getAddressList()))
        .name(user.getName()).gender(user.getGender()).contact(
            user.getContact()).build();
    userEntity.getAddressList()
        .forEach(addressEntity -> addressEntity.setUser(userEntity));
    return userEntity;
  }

  public List<Address> addressEntityListToAddressList(List<AddressEntity> list) {
    List<Address> addressList = new ArrayList<>();
    for (AddressEntity addressEntity : list) {
      addressList.add(Address.builder().province(addressEntity.getProvince())
          .zipCode(addressEntity.getZipCode()).defaultAddress(addressEntity.getDefaultAddress())
          .detailAddress(
              addressEntity.getDetailAddress()).roadAddress(addressEntity.getRoadAddress())
          .build());
    }
    return addressList;
  }

  public List<AddressEntity> addressListToAddressEntityList(List<Address> addressList) {
    List<AddressEntity> addressEntityList = new ArrayList<>();
    for (Address address : addressList) {
      AddressEntity addressEntity = AddressEntity.builder().province(address.getProvince())
          .zipCode(address.getZipCode()).defaultAddress(address.isDefaultAddress()).detailAddress(
              address.getDetailAddress()).roadAddress(address.getRoadAddress()).build();

      addressEntityList.add(addressEntity);
    }
    return addressEntityList;
  }
}

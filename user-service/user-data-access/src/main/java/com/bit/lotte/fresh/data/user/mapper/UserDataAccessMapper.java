package com.bit.lotte.fresh.data.user.mapper;

import com.bit.lotte.fresh.data.user.entity.AddressEntity;
import com.bit.lotte.fresh.data.user.entity.UserEntity;
import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {


  public User userEntityToUser(UserEntity userEntity) {

    User user = User.builder()
        .addressList(addressEntityListToAddressList(userEntity.getAddressList()))
        .name(userEntity.getName()).contact(
            userEntity.getContact()).build();
    user.setEntityId(new UserId(userEntity.getId()));
    return user;
  }

  public UserEntity userToUserEntity(User user) {

        UserEntity userEntity = UserEntity.builder().id(user.getEntityId().getValue())
        .addressList(addressListToAddressEntityList(user.getAddressList()))
        .name(user.getName()).contact(
            user.getContact()).build();
    userEntity.getAddressList()
        .forEach(addressEntity -> addressEntity.setUser(userEntity));
    return userEntity;
  }

  public List<Address> addressEntityListToAddressList(List<AddressEntity> list) {
    List<Address> addressList = new ArrayList<>();
    for (AddressEntity addressEntity : list) {

      Address address = Address.builder().entityId(new AddressId(addressEntity.getId()))
          .province(addressEntity.getProvince())
          .zipCode(addressEntity.getZipCode()).defaultAddress(addressEntity.getDefaultAddress())
          .detailAddress(
              addressEntity.getDetailAddress())
          .build();
      address.setEntityId(new AddressId(addressEntity.getId()));
      addressList.add(address);
    }
    return addressList;
  }

  public List<AddressEntity> addressListToAddressEntityList(List<Address> addressList) {
    List<AddressEntity> addressEntityList = new ArrayList<>();
    for (Address address : addressList) {
      AddressEntity addressEntity = AddressEntity.builder().province(address.getProvince())
          .zipCode(address.getZipCode()).defaultAddress(address.isDefaultAddress()).detailAddress(
              address.getDetailAddress()).build();

      addressEntityList.add(addressEntity);
    }
    return addressEntityList;
  }
}

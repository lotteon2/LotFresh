package com.bit.lotte.fresh.domain.entity;

import com.bit.lotte.fresh.domain.exception.CanNotDeleteAddressUnder0Exception;
import com.bit.lotte.fresh.domain.exception.CanNotDeleteDefaultAddressException;
import com.bit.lotte.fresh.domain.exception.RegexValidationException;
import com.bit.lotte.fresh.domain.exception.UserDomainException;
import com.bit.lotte.fresh.domain.valueobject.Gender;
import com.bit.lotte.fresh.user.common.entity.AggregateRoot;
import com.bit.lotte.fresh.user.common.valueobject.AddressId;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class User extends AggregateRoot<UserId> {

  private final int MINIMUM_ADDRESS_NUMBER = 1;
  private String name;
  private String contact;
  private List<Address> addressList;
  private ZonedDateTime createdTime;
  private ZonedDateTime recentUpdatedTime;


  public void setDefaultAddressWithOnlyOneAddress(User user) {
    if (user.getAddressList().size() <= 1) {
      user.getAddressList().get(0).setDefaultAddress(true);
    }
  }

  public void initUser(User user) {
    validateRegex(user);
    setDefaultAddressWithOnlyOneAddress(user);
  }

  public void validateRegex(User user) {
    validateContact(user);
    validateName(user);
  }

  public void validateAddressRegex(Address address) {
    address.validateZipCode(address);
  }

  public void validateName(User user) {
    String userName = user.getName();
    if (userName == null) {
      throw new RegexValidationException("이름은 공백일 수 없습니다.");
    }
    String[] nameParts = userName.split("\\s+");
    if (!(nameParts.length >= 2 && nameParts.length <= 10)) {
      throw new RegexValidationException("이름은 최소 2글자 이상 10글자 이하여야합니다.");
    }
  }

  public Address getAddressById(AddressId addressId){
    for(Address address: addressList){
      if (address.getEntityId().equals(addressId)) {
        return address;
      }
    }
    throw new UserDomainException("해당 주소록을 찾을 수 없습니다.");
  }

  public void validateContact(User user) {
    String contact = user.getContact();
    if (contact == null) {
      throw new RegexValidationException("연락처는 공백일 수 없습니다.");
    }
    if (!contact.matches("\\d{3}-\\d{4}-\\d{4}")) {
      throw new RegexValidationException("연락처 형식은 000-0000-0000 이여야합니다.");
    }
  }


  public void addAddress(Address address) {
    validateAddressRegex(address);
    addressList.add(address);
  }

  public void deleteAddress(Address address) {
    addressNumberCheck(addressList);
    checkAddressIsDefault(address);
    addressList.remove(address);
  }


  public void changeDefaultAddress(Address newDefaultAddress){
    getUserDefaultAddress().setDefaultAddress(false);
    newDefaultAddress.setDefaultAddress(true);
  }

  public void addressNumberCheck(List<Address> list) {
    if (list.size() <= 1) {
      throw new CanNotDeleteAddressUnder0Exception("주소지는 최소 1개를 가지고 있어야합니다.");
    }
  }

  public void checkAddressIsDefault(Address address) {
    if (address.isDefaultAddress()) {
      throw new CanNotDeleteDefaultAddressException("기본 주소지는 삭제할 수 없습니다. 기본 주소지를 변경 후에 삭제해주세요");
    }
  }

  public Address getUserDefaultAddress() {
    for (Address address : addressList) {
      if (address.isDefaultAddress()) {
        return address;
      }
    }
    return null;
  }


}



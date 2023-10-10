package com.bit.lotte.fresh.domain;

import static com.bit.lotte.fresh.user.common.valueobject.DomainConstants.UTC;

import com.bit.lotte.fresh.domain.entity.Address;
import com.bit.lotte.fresh.domain.entity.User;
import com.bit.lotte.fresh.domain.event.address.AddUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.ChangeDefaultUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.address.DeleteUserAddressDomainEvent;
import com.bit.lotte.fresh.domain.event.user.CreateUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.DeleteUserDomainEvent;
import com.bit.lotte.fresh.domain.event.user.GetUserInfoDomainEvent;
import com.bit.lotte.fresh.domain.event.user.UpdateUserDomainEvent;
import com.bit.lotte.fresh.user.common.valueobject.UserId;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDomainServiceImpl implements UserDomainService {

    @Override
    public GetUserInfoDomainEvent getUser(User user) {
        return new GetUserInfoDomainEvent(user, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public CreateUserDomainEvent createUser(User user) {
        return new CreateUserDomainEvent(user, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public DeleteUserDomainEvent deleteUser(User user) {
        return new DeleteUserDomainEvent(user, ZonedDateTime.now(ZoneId.of(UTC)));
    }


    @Override
    public UpdateUserDomainEvent updateUser(User user) {
        return new UpdateUserDomainEvent(user,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public AddUserAddressDomainEvent addUserAddress(User user, Address address){
            user.getAddressList().add(address);
            return new AddUserAddressDomainEvent(user,address,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public ChangeDefaultUserAddressDomainEvent changeDefaultAddress(User user,Address newDefaultAddress)
    {
        user.changeDefaultAddress(newDefaultAddress);
        return new ChangeDefaultUserAddressDomainEvent(user,newDefaultAddress,ZonedDateTime.now(ZoneId.of(UTC)));

    }

    @Override
    public DeleteUserAddressDomainEvent deleteUserAddress(User user, Address address) {
        user.deleteAddress(address);
        return new DeleteUserAddressDomainEvent(user,address, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}


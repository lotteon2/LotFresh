package com.lotfresh.userservice.domain.address.repository.custom;

import com.lotfresh.userservice.domain.address.entity.Address;

import java.util.Optional;

public interface AddressRepositoryCustom {

    Optional<Address> findDefaultAddressByMemberId(Long memeberId);
}

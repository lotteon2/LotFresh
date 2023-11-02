package com.lotfresh.userservice.domain.address.repository;

import com.lotfresh.userservice.domain.address.entity.Address;
import com.lotfresh.userservice.domain.address.repository.custom.AddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {}

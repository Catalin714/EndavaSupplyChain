package com.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychain.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

package com.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychain.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

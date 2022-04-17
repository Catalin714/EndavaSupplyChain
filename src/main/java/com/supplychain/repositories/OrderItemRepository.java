package com.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplychain.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

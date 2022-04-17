package com.supplychain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.supplychain.domain.Order;
import com.supplychain.domain.OrderStatus;
import com.supplychain.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

	List<Order> findByUserEmail(String email);

	@Modifying
	@Query("update Order o set o.status = :status where o.id = :id")
	@Transactional
	void changeStatus(@Param("status") OrderStatus status, @Param("id") Long id);

	@Modifying
	@Query("update Order o set o.manufacturer = :user where o.id = :id")
	@Transactional
	void affectManufacturer(@Param("user") User user, @Param("id") Long id);

}

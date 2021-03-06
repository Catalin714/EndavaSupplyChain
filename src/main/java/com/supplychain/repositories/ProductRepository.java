package com.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supplychain.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

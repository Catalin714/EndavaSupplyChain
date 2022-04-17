package com.supplychain.domain.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.supplychain.domain.Product;
import com.supplychain.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	public void save(Product role) {
		repository.save(role);

	}

	public void remove(Product role) {
		repository.delete(role);
	}

	public void update(Product role) {
		repository.save(role);
	}

	public List<Product> findAll() {
		return repository.findAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Page<Product> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}

}

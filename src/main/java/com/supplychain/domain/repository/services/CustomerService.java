package com.supplychain.domain.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supplychain.domain.Customer;
import com.supplychain.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public void save(Customer role) {
		repository.save(role);

	}

	public void remove(Customer role) {
		repository.delete(role);
	}

	public void update(Customer role) {
		repository.save(role);
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Customer findOne(Long id) {
		return repository.findById(id).get();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Page<Customer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

}

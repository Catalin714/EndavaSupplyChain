package com.supplychain.domain.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supplychain.domain.Address;
import com.supplychain.repositories.AddressRepository;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository repository;

	public void save(Address role) {
		repository.save(role);
	}

	public void remove(Address role) {
		repository.delete(role);
	}

	public void update(Address role) {
		repository.save(role);
	}

	public List<Address> findAll() {
		return repository.findAll();
	}

	public Address findOne(Long id) {
		return repository.findById(id).get();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Page<Address> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<Address> findById(Long id) {
		return repository.findById(id);
	}

}

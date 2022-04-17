package com.supplychain.domain.repository.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supplychain.domain.Role;
import com.supplychain.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository repository;

	public void save(Role role) {
		repository.save(role);

	}

	public void remove(Role role) {
		repository.delete(role);
	}

	public void update(Role role) {
		repository.save(role);
	}

	public List<Role> findAll() {
		return repository.findAll();
	}

	public Role findOne(Long id) {
		return repository.findById(id).get();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Page<Role> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<Role> findById(Long id) {
		return repository.findById(id);
	}

}

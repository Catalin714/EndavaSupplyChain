package com.supplychain.domain.repository.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supplychain.domain.User;
import com.supplychain.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public List<User> findByRolesRoleName(String role) {
		return userRepository.findByRoles_RoleName(role);
	}

	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public ResponseEntity<String> createUser(User user) {
		try {
			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User has been created!(" + HttpStatus.CREATED + ")");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user cannot be created!", e);
		}
	}

	public ResponseEntity<String> deleteUser(Long userID) {
		try {
			userRepository.deleteById(userID);
			return ResponseEntity.status(HttpStatus.OK).body("User has been deleted!(" + HttpStatus.OK + ")");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user cannot be removed!", e);
		}
	}

	@Transactional
	public ResponseEntity<String> editUser(User user) {
		try {
			User newUser = userRepository.findById(user.getId()).get();
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setEmail(user.getEmail());
			userRepository.save(newUser);
			return ResponseEntity.status(HttpStatus.OK).body("User has been edited!(" + HttpStatus.OK + ")");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user cannot be edited!", e);
		}
	}

}

package com.iiht.eauction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.eauction.model.User;
import com.iiht.eauction.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Boolean existsByFirstName(String username) {
		return userRepository.existsByFirstName(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);

	}

}

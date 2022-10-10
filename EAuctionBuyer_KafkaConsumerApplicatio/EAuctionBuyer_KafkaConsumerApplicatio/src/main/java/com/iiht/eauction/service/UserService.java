package com.iiht.eauction.service;

import java.util.Optional;

import com.iiht.eauction.model.User;

public interface UserService {

	Optional<User> findByFirstName(String firstName);

	Optional<User> findByEmail(String email);

	Boolean existsByFirstName(String username);

	Boolean existsByEmail(String email);

	void save(User user);

}

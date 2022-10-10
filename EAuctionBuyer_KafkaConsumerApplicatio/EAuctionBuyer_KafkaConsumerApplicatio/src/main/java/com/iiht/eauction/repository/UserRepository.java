package com.iiht.eauction.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iiht.eauction.model.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByFirstName(String firstName);
  Optional<User> findByEmail(String email);

  Boolean existsByFirstName(String username);

  Boolean existsByEmail(String email);
}

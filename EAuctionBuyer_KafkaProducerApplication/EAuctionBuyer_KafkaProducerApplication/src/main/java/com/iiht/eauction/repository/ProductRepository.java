package com.iiht.eauction.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iiht.eauction.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	 Optional<Product> findByProductName(String productName);
	 Optional<Product> existsByProductName(String productName);
	 

	
	
}

package com.iiht.eauction.seller.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iiht.eauction.seller.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	 Optional<Product> findByProductName(String productName);
	 Optional<Product> existsByProductName(String productName);
	 

	
	
}

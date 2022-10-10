package com.iiht.eauction.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iiht.eauction.model.BidModel;
import com.iiht.eauction.model.ERole;
import com.iiht.eauction.model.Role;


public interface BidsRepository extends MongoRepository<BidModel, String> {
}

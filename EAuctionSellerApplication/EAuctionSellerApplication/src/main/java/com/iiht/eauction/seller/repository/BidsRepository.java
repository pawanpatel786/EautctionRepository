package com.iiht.eauction.seller.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.iiht.eauction.seller.model.BidModel;
import com.iiht.eauction.seller.model.ERole;
import com.iiht.eauction.seller.model.Role;


public interface BidsRepository extends MongoRepository<BidModel, String> {
}

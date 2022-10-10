package com.iiht.eauction.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = BidsRepository.class)
@Configuration
public class MongoDBconfig {

}

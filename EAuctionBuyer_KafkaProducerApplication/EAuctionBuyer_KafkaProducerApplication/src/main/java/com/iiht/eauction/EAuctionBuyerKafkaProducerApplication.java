package com.iiht.eauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EAuctionBuyerKafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionBuyerKafkaProducerApplication.class, args);
	}

}

package com.iiht.eauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EAuctionBuyerKafkaConsumerApplicatioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionBuyerKafkaConsumerApplicatioApplication.class, args);
	}

}

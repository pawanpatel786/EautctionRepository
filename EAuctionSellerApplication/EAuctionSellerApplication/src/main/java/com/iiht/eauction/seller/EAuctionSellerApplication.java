package com.iiht.eauction.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EAuctionSellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionSellerApplication.class, args);
	}

}

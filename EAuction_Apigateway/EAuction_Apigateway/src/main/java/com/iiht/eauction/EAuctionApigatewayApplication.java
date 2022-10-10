package com.iiht.eauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class EAuctionApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionApigatewayApplication.class, args);
	}

}

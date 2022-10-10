package com.iiht.eauction.kafka.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiht.eauction.model.BidModel;
import com.iiht.eauction.model.Product;
import com.iiht.eauction.repository.BidsRepository;
import com.iiht.eauction.repository.ProductRepository;

@Service
public class KafKaConsumer {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	BidsRepository bidRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

	@KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
	public void consume(String bidModel) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			BidModel bidRequest = objectMapper.readValue(bidModel, BidModel.class);
			LOGGER.info(String.format("Message received -> %s", bidRequest.getBidPrice()));

			Optional<Product> productOptional = productRepository.findById(bidRequest.getProductId());

			Product product = productOptional.get();
			BidModel savedBid = bidRepository.save(bidRequest);

			product.getBids().add(savedBid);
			productRepository.save(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.info(e.getMessage());
		}
	}
}

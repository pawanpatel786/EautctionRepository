package com.iiht.eauction.kafka.config;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.iiht.eauction.model.BidModel;

@Service
public class KafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {

//		kafkaTemplate.send(AppConstants.TOPIC_NAME, new DataRecordAvro(1l).toString());
		kafkaTemplate.send(AppConstants.TOPIC_NAME, message);

		LOGGER.info(String.format("Message sent -> %s", message));
	}
	
	public boolean sendMessage(BidModel bidModel) {

		ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(AppConstants.TOPIC_NAME, bidModel.toString());
try {
	if(send.get().getProducerRecord().value()!=null) { 
		return true;
	}
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (ExecutionException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		LOGGER.info(String.format("Message sent -> %s", bidModel.toString()));
		return false;
	}

}

//package com.newswave.user.kafka;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class KafkaProducer {
//
//	private KafkaTemplate<String, String> kafkaTemplate;
//	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//	    this.kafkaTemplate = kafkaTemplate;
//	  }
//	
//	public void sendMessage(String message) {
//	    log.info(String.format("Message sent %s", message));
//	    kafkaTemplate.send(KafkaConfig.TOPIC_NAME, message);
//	  }
//}

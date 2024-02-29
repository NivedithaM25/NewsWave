//package com.newswave.user.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaConfig {
//
//	public static final String TOPIC_NAME = "UserResponse";
//
//	  @Bean
//	  public NewTopic userTopic() {
//	      return TopicBuilder.name(TOPIC_NAME)
//	        .build();
//	  }
//}

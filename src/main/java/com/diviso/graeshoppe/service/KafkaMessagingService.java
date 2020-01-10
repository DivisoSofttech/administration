package com.diviso.graeshoppe.service;


import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.config.KafkaProperties;

@Service
public class KafkaMessagingService {

	private final Logger log = LoggerFactory.getLogger(KafkaMessagingService.class);

	@Value("${topic.cancellation.destination}")
	private String cancellation;
	
	private final KafkaProperties kafkaProperties;
	
	public KafkaMessagingService(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	/*
	 * public PublishResult publishOrder(Order message) throws ExecutionException,
	 * InterruptedException { RecordMetadata metadata = orderProducer.send(new
	 * ProducerRecord<>(orderTopic, message)).get(); return new
	 * PublishResult(metadata.topic(), metadata.partition(), metadata.offset(),
	 * Instant.ofEpochMilli(metadata.timestamp())); }
	 */

}

package com.diviso.graeshoppe.service;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.avro.*;
import com.diviso.graeshoppe.config.KafkaProperties;

@Service
public class KafkaMessagingService {

	private final Logger log = LoggerFactory.getLogger(KafkaMessagingService.class);

	@Value("${topic.cancellation.destination}")
	private String cancellationTopic;
	
	@Value("${topic.refund.destination}")
	private String refundTopic;

	private KafkaProducer<String, CancellationRequest> cancellationRequestProducer;
	private KafkaProducer<String, RefundDetails> refundDetailsProducer;

	public KafkaMessagingService(KafkaProperties kafkaProperties) {
		this.cancellationRequestProducer = new KafkaProducer<>(kafkaProperties.getProducerProps());
		this.refundDetailsProducer = new KafkaProducer<>(kafkaProperties.getProducerProps());

	}

	public PublishResult publishCancellationRequest(CancellationRequest cancellationRequest) throws ExecutionException, InterruptedException {
		RecordMetadata metadata = cancellationRequestProducer.send(new ProducerRecord<>(cancellationTopic, cancellationRequest)).get();
		return new PublishResult(metadata.topic(), metadata.partition(), metadata.offset(),
				Instant.ofEpochMilli(metadata.timestamp()));
	}
	
	public PublishResult publishrefundDetails(RefundDetails refundDetails) throws ExecutionException, InterruptedException {
		RecordMetadata metadata = refundDetailsProducer.send(new ProducerRecord<>(refundTopic, refundDetails)).get();
		return new PublishResult(metadata.topic(), metadata.partition(), metadata.offset(),
				Instant.ofEpochMilli(metadata.timestamp()));
	}
	
	public static class PublishResult {
		public final String topic;
		public final int partition;
		public final long offset;
		@Override
		public String toString() {
			return String.format("PublishResult [topic=%s,\n partition=%s,\n offset=%s,\n timestamp=%s]", topic,
					partition, offset, timestamp);
		}

		public final Instant timestamp;

		private PublishResult(String topic, int partition, long offset, Instant timestamp) {
			this.topic = topic;
			this.partition = partition;
			this.offset = offset;
			this.timestamp = timestamp;
		}
	}

}

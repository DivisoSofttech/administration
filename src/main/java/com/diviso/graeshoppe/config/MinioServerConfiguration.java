package com.diviso.graeshoppe.config;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

@Configuration
public class MinioServerConfiguration {

	@Value("${minio.server.url}")
	private String url;

	@Value("${minio.server.accessKey}")
	private String accesskey;

	@Value("${minio.server.secretKey}")
	private String secretKey;

	@Value("${minio.buckets.product}")
	private String productBucketName;

	@Value("${minio.buckets.category}")
	private String categoryBucketName;

	@Bean
	public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
		MinioClient minioClient = new MinioClient(url, accesskey, secretKey);
		try {
			boolean productBucketFound = minioClient.bucketExists(productBucketName);
			boolean categoryBucketFound = minioClient.bucketExists(categoryBucketName);
			if (productBucketFound) {
				Log.info("ProductBucket already exists " + productBucketName);
			} else {
				minioClient.makeBucket(productBucketName);
				Log.info("ProductBucket created " + productBucketName);
			}

			if (categoryBucketFound) {
				Log.info("CategoryBucket already exists " + categoryBucketName);
			} else {
				minioClient.makeBucket(categoryBucketName);
				Log.info("CategoryBucket created " + categoryBucketName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return minioClient;
	}

}

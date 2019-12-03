package com.diviso.graeshoppe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diviso.graeshoppe.service.ImageService;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

@Configuration
public class MinioServerConfiguration {
	private final Logger log = LoggerFactory.getLogger(ImageService.class);


	@Value("${minio.server.url}")
	private String url;

	@Value("${minio.server.accessKey}")
	private String accesskey;

	@Value("${minio.server.secretKey}")
	private String secretKey;

	@Value("${minio.buckets.premium-banner}")
	private String premiumBannerBucket;


	@Bean
	public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
		MinioClient minioClient = new MinioClient(url, accesskey, secretKey);
		try {
			boolean premiumBannerBucketFound = minioClient.bucketExists(premiumBannerBucket);
			if (premiumBannerBucketFound) {
				log.info("premiumBannerBucket already exists " + premiumBannerBucket);
			} else {
				minioClient.makeBucket(premiumBannerBucket);
				log.info("premiumBannerBucket created " + premiumBannerBucket);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return minioClient;
	}

}

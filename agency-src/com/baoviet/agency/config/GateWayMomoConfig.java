package com.baoviet.agency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Properties specific to API service.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "payment.momo", ignoreUnknownFields = false)
@Data
public class GateWayMomoConfig extends PaymentConfig {
	private String serviceUrl;
	private String returnUrlAndroid;
	private String returnUrlIos;
	private String returnUrl;
	private String notifyUrl;
	private String partnerCode;
	private String accessKey;
	private String secretKey;
}

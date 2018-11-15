package com.baoviet.agency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Properties specific to API service.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "payment.viettel-pay", ignoreUnknownFields = false)
@Data
public class GateWayViettelPayConfig extends PaymentConfig {
	private String serviceUrl;
	private String postUrl;
	private String returnUrl;
	private String version;
	private String command;
	private String merchantCode;
	private String accessCode;
	private String hashKey;
	private String currency;
}

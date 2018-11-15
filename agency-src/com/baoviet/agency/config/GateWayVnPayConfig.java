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
@ConfigurationProperties(prefix = "payment.vn-pay", ignoreUnknownFields = false)
@Data
public class GateWayVnPayConfig extends PaymentConfig {
	private String serviceUrl;
	private String returnUrl;
	private String querydr;
	private String tmnCode;
	private String hashSecret;
	private String version;
	private String command;
	private String currency;
}

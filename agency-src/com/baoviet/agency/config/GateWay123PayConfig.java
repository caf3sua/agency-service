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
@ConfigurationProperties(prefix = "payment.l23-pay", ignoreUnknownFields = false)
@Data
public class GateWay123PayConfig extends PaymentConfig {
	private String serviceUrl;
	private String returnUrl;
	private String cancelUrl;
	private String errorUrl;
	private String clientIp;
}

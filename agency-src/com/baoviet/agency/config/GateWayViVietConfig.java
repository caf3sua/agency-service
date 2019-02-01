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
@ConfigurationProperties(prefix = "payment.vi-viet", ignoreUnknownFields = false)
@Data
public class GateWayViVietConfig extends PaymentConfig {
	private String serviceUrl;
	private String returnUrl;
	private String merchant;
	private String merchantSiteId;
	private String accessCode;
	private String sercuritySecret;
	private String version;
	private String clientIp;
}

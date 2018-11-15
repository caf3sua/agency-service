package com.baoviet.agency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Properties specific to API service.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {
	private String httpProxyAddress;
	private int httpProxyPort;
	private String paymentReturnPage;
	private String paymentMobileReturnPage;
}

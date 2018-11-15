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
@Data
public class PaymentConfig {
	private String cid;
	private String did;
}

package com.baoviet.agency.config;

import io.github.jhipster.config.JHipsterConstants;

import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.DispatcherServlet;

import com.baoviet.agency.aop.logging.LoggingAspect;
import com.baoviet.agency.utils.logging.dispatcher.LoggableDispatcherServlet;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

	@Bean
	@Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
	public LoggingAspect loggingAspect(Environment env) {
		return new LoggingAspect(env);
	}

	@Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
	public DispatcherServlet dispatcherServlet() {
		return new LoggableDispatcherServlet();
	}
}

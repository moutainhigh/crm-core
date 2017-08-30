package com.cafe.crm.configs.loggers;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Loggers {

	@Bean(name = "logger")
	public Logger getLogger() {
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger log = logCtx.getLogger("org.springframework.web");
		log.setAdditive(false);
		return log;
	}

	@Bean(name = "hibLogger")
	public Logger getHibLogger() {
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		return logCtx.getLogger("org.hibernate");
	}
}

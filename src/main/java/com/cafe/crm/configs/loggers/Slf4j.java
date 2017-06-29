package com.cafe.crm.configs.loggers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Slf4j {

	@Bean(name = "logger")
	public Logger getLogger() {
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger log = logCtx.getLogger("org.springframework.web");
		log.setAdditive(false);
		log.setLevel(Level.INFO);
		return log;
	}

}

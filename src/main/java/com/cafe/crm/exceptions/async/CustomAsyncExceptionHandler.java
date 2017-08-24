package com.cafe.crm.exceptions.async;


import ch.qos.logback.classic.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.Method;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Autowired
	@Qualifier(value = "logger")
	private Logger log;

	public CustomAsyncExceptionHandler() {
	}

	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
		log.error("Asynchronous Exception :" + throwable.getMessage());
		log.error("Method name :" + method.getName());
		for (final Object parameter : objects) {
			log.error("Parameter :" + parameter);
		}
	}
}

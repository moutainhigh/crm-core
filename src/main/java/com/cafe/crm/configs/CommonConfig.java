package com.cafe.crm.configs;

import com.cafe.crm.initMet.InitClient;
import com.cafe.crm.initMet.InitMenu;
import com.cafe.crm.initMet.InitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
	@Bean(initMethod = "init")
	public InitMenu initTestData() {
		return new InitMenu();
	}
	@Bean(initMethod = "init")
	public InitClient initTestClient() {
		return new InitClient();
	}
	@Bean(initMethod = "init")
	public InitProperties initProperties() {
		return new InitProperties();
	}
}

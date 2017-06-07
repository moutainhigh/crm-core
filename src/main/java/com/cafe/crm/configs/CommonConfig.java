package com.cafe.crm.configs;

import com.cafe.crm.initMet.InitClient;
import com.cafe.crm.initMet.InitMenu;
import com.cafe.crm.initMet.InitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@ComponentScan("src/main/")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

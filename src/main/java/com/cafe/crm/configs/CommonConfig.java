package com.cafe.crm.configs;

import com.cafe.crm.configs.property.AdvertisingProperties;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.HashMap;
import java.util.HashSet;

@Configuration
@EnableAspectJAutoProxy
public class CommonConfig {

	private final AdvertisingProperties advertisingProperties;

	@Autowired
	public CommonConfig(AdvertisingProperties properties) {
		this.advertisingProperties = properties;
	}

	@Bean
	public Cloudinary cloudinary() {
		HashMap<String, String> config = new HashMap<>();
		config.put("cloud_name", advertisingProperties.getCloud().getName());
		config.put("api_key", advertisingProperties.getCloud().getKey());
		config.put("api_secret", advertisingProperties.getCloud().getSecret());
		return new Cloudinary(config);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TemplateResolver springThymeleafTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setOrder(1);
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean
	public TemplateResolver dbTemplateResolver(){
		DbTemplateResolver resolver = new DbTemplateResolver();
		resolver.setOrder(2);
		return resolver;
	}

	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		HashSet<TemplateResolver> templateResolvers = new HashSet<>();
		templateResolvers.add(springThymeleafTemplateResolver());
		templateResolvers.add(dbTemplateResolver());
		engine.setTemplateResolvers(templateResolvers);
		return engine;
	}

}

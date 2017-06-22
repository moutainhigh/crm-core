package com.cafe.crm.configs;

import com.cafe.crm.configs.property.AdvertisingProperties;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

@Configuration
public class CommonConfig {

	private final AdvertisingProperties advertisingProperties;

	@Autowired
	public CommonConfig(AdvertisingProperties properties) {
		this.advertisingProperties = properties;
	}

	@Bean
	public Cloudinary cloudinary(){
		HashMap<String, String> config = new HashMap<>();
		config.put("cloud_name", advertisingProperties.getCloud().getName());
		config.put("api_key", advertisingProperties.getCloud().getKey());
		config.put("api_secret", advertisingProperties.getCloud().getSecret());
		return new Cloudinary(config);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

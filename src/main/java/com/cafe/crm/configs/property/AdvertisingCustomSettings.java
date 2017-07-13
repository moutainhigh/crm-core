package com.cafe.crm.configs.property;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingCustomSettings {

	@Autowired
	private AdvertisingSettingsService settingsService;

	@Autowired
	private JavaMailSender javaMailSender;


	public JavaMailSenderImpl getCustomSettings() {
		return (JavaMailSenderImpl) javaMailSender;
	}

}

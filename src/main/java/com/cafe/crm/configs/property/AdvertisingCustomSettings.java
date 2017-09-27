package com.cafe.crm.configs.property;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingCustomSettings {

	private final JavaMailSender javaMailSender;

	@Autowired
	public AdvertisingCustomSettings(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public JavaMailSenderImpl getCustomSettings() {
		return (JavaMailSenderImpl) javaMailSender;
	}

}

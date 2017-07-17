package com.cafe.crm.configs.property;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingCustomSettings {

    @Autowired
    private JavaMailSender javaMailSender;


    public JavaMailSenderImpl getCustomSettings() {
        return (JavaMailSenderImpl) javaMailSender;
    }

}

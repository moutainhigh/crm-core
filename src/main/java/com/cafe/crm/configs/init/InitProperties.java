package com.cafe.crm.configs.init;

import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.models.property.Property;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitProperties {

	@Autowired
	private AdvertisingSettingsService advertisingSettingsService;

	@Autowired
	private PropertyService propertyService;

	@PostConstruct
	public void init() {
		Property property = new Property("Базовая скидка карты", 5D, true);
		Property property1 = new Property("Бонус от реферала", 10D, true);
		Property property2 = new Property("Реферальный бонус", 150D, true);
		Property property3 = new Property("Тестовая настройка", 16D, true);

		propertyService.save(property);
		propertyService.save(property1);
		propertyService.save(property2);
		propertyService.save(property3);
	}

	@PostConstruct
	public void initAdvertSettings() {
		AdvertisingSettings settings = new AdvertisingSettings("Test", "nikitaunmortal@gmail.com", "some", "smtp.gmail.com");

		advertisingSettingsService.save(settings);
	}
}

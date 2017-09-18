package com.cafe.crm.configs.init;

import com.cafe.crm.models.property.Property;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitProperties {

	private final AdvertisingSettingsService advertisingSettingsService;
	private final PropertyService propertyService;

	@Autowired
	public InitProperties(AdvertisingSettingsService advertisingSettingsService, PropertyService propertyService) {
		this.advertisingSettingsService = advertisingSettingsService;
		this.propertyService = propertyService;
	}

	@PostConstruct
	public void init() {
		Property property = new Property("price.firstHour", 300D, "р", null);
		Property property1 = new Property("price.nextHours", 200D, "р", null);
		Property property2 = new Property("price.refBonus", 150D, "р", true);
		propertyService.save(property);
		propertyService.save(property1);
		propertyService.save(property2);

	}

}

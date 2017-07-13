package com.cafe.crm.configs.init;

import com.cafe.crm.models.property.Property;
import com.cafe.crm.services.interfaces.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitProperties {

	@Autowired
	private PropertyService propertyService;

	@PostConstruct
	public void init() {
		Property property = new Property("Цена за первый час", 300D, "р", null );
		Property property1 = new Property("Цена за остальные часы", 200D, "р", null);
		Property property2 = new Property("Реферальный бонус", 150D,"р", true);

		propertyService.save(property);
		propertyService.save(property1);
		propertyService.save(property2);

	}
}

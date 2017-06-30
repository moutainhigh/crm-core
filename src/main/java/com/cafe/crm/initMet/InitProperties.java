package com.cafe.crm.initMet;

import com.cafe.crm.models.property.Property;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitProperties {

	@Autowired
	private PropertyService propertyService;

	@PostConstruct
	public void	init() {
		Property property = new Property("Базовая скидка карты",5L,true);
		Property property1 = new Property("Бонус от реферала",10L,true);
		Property property2= new Property("Реферальный бонус",150L,true);
		Property property3= new Property("Тестовая настройка",16L,true);

		propertyService.save(property);
		propertyService.save(property1);
		propertyService.save(property2);
		propertyService.save(property3);
	}
}

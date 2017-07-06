package com.cafe.crm.services.impl.property;


import com.cafe.crm.models.property.Property;
import com.cafe.crm.repositories.property.PropertyRepository;
import com.cafe.crm.services.interfaces.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Override
	public void save(Property property) {
		propertyRepository.saveAndFlush(property);
	}

	@Override
	public List<Property> findAll() {
		return propertyRepository.findAll();
	}

	@Override
	public Property getOne(Long id) {
		return propertyRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		propertyRepository.delete(id);
	}

	@Override
	public void saveCollection(List<Property> list) {
		propertyRepository.save(list);
	}

}

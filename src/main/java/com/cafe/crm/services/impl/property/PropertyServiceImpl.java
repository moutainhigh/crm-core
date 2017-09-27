package com.cafe.crm.services.impl.property;


import com.cafe.crm.models.property.Property;
import com.cafe.crm.repositories.property.PropertyRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.property.PropertyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public PropertyServiceImpl(PropertyRepository propertyRepository, CompanyService companyService) {
		this.propertyRepository = propertyRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Property property) {
		property.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void save(Property property) {
		setCompanyId(property);
		propertyRepository.saveAndFlush(property);
	}

	@Override
	public List<Property> findAll() {
		return propertyRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public Property getByName(String name) {
		return propertyRepository.getByNameAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public void delete(Long id) {
		propertyRepository.delete(id);
	}

	@Override
	public void saveCollection(List<Property> properties) {
		for (Property property : properties){
			setCompanyId(property);
		}
			propertyRepository.save(properties);
	}

}

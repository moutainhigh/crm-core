package com.cafe.crm.services.interfaces.property;


import com.cafe.crm.models.property.AllSystemProperty;

import java.util.List;

public interface SystemPropertyService {

	void save(AllSystemProperty property);

	void saveMasterKey(String newMasterKey);

	void delete(Long id);

	void delete(String name);

	void delete(AllSystemProperty property);

	AllSystemProperty findOne(Long id);

	AllSystemProperty findOne(String name);

	List<AllSystemProperty> findAll();

}

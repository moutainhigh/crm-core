package com.cafe.crm.services.impl.property;


import com.cafe.crm.models.company.Company;
import com.cafe.crm.models.property.AllSystemProperty;
import com.cafe.crm.repositories.property.SystemPropertyRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.property.SystemPropertyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

	@Autowired
	private SystemPropertyRepository repository;
	private CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompany(AllSystemProperty allSystemProperty) {
		Long companyId = companyIdCache.getCompanyId();
		Company company = companyService.findOne(companyId);
		allSystemProperty.setCompany(company);
	}

	@Override
	public void save(AllSystemProperty property) {
		setCompany(property);
		repository.save(property);
	}

	@Override
	public void saveMasterKey(String newMasterKey) {
		AllSystemProperty masterKey = repository.findByNameIgnoreCaseAndCompanyId("masterKey", companyIdCache.getCompanyId());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (masterKey == null) {
			masterKey = new AllSystemProperty();
			masterKey.setName("masterKey");
			masterKey.setProperty(passwordEncoder.encode(newMasterKey));
		} else {
			masterKey.setProperty(passwordEncoder.encode(newMasterKey));
		}

		repository.save(masterKey);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public void delete(String name) {
		repository.deleteByNameAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public void delete(AllSystemProperty property) {
		repository.delete(property);
	}

	@Override
	public AllSystemProperty findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public AllSystemProperty findOne(String name) {
		return repository.findByNameIgnoreCaseAndCompanyId(name, companyIdCache.getCompanyId());
	}

	@Override
	public List<AllSystemProperty> findAll() {
		return repository.findByCompanyId(companyIdCache.getCompanyId());
	}
}

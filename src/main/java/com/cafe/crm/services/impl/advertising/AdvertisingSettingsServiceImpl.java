package com.cafe.crm.services.impl.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.repositories.advertising.AdvertisingSettingsRepository;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingSettingsServiceImpl implements AdvertisingSettingsService {

	private final AdvertisingSettingsRepository repository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public AdvertisingSettingsServiceImpl(AdvertisingSettingsRepository repository, CompanyService companyService) {
		this.repository = repository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(AdvertisingSettings advertisingSettings) {
		advertisingSettings.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public void save(AdvertisingSettings settings) {
		setCompanyId(settings);
		repository.save(settings);
	}

	@Override
	public void delete(AdvertisingSettings settings) {
		repository.delete(settings);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public AdvertisingSettings get(Long id) {
		return repository.findOne(id);
	}

	@Override
	public AdvertisingSettings findByEmail(String email) {
		return repository.findByEmailIgnoreCaseAndCompanyId(email, companyIdCache.getCompanyId());
	}

	@Override
	public List<AdvertisingSettings> getAll() {
		return repository.findByCompanyId(companyIdCache.getCompanyId());
	}

}

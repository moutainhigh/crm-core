package com.cafe.crm.services.interfaces.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;

import java.util.List;

public interface AdvertisingSettingsService {

	void save(AdvertisingSettings settings);

	void delete(AdvertisingSettings settings);

	void delete(Long id);

	AdvertisingSettings get(Long id);

	AdvertisingSettings findByEmail(String email);

	List<AdvertisingSettings> getAll();
}

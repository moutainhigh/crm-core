package com.cafe.crm.repositories.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisingSettingsRepository extends JpaRepository<AdvertisingSettings, Long> {

	AdvertisingSettings findByEmailIgnoreCaseAndCompanyId(String senderMail, Long companyId);

	List<AdvertisingSettings> findByCompanyId (Long companyId);
}

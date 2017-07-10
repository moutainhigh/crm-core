package com.cafe.crm.repositories.advertising;


import org.springframework.data.jpa.repository.JpaRepository;
import com.cafe.crm.models.advertising.AdvertisingSettings;

public interface AdvertisingSettingsRepository extends JpaRepository<AdvertisingSettings, Long> {
}

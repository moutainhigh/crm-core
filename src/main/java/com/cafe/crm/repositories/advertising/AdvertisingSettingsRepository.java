package com.cafe.crm.repositories.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisingSettingsRepository extends JpaRepository<AdvertisingSettings, Long> {

    AdvertisingSettings findByEmailIgnoreCase(String senderMail);

}

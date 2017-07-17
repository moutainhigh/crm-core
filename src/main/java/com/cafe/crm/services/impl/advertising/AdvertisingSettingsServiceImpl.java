package com.cafe.crm.services.impl.advertising;


import com.cafe.crm.models.advertising.AdvertisingSettings;
import com.cafe.crm.repositories.advertising.AdvertisingSettingsRepository;
import com.cafe.crm.services.interfaces.advertising.AdvertisingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingSettingsServiceImpl implements AdvertisingSettingsService {

    @Autowired
    AdvertisingSettingsRepository repository;

    @Override
    public void save(AdvertisingSettings settings) {
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
        return repository.findByEmailIgnoreCase(email);
    }

    @Override
    public List<AdvertisingSettings> getAll() {
        return repository.findAll();
    }

}

package com.cafe.crm.service_impl.client;

import com.cafe.crm.dao.client.CalculateRepository;
import com.cafe.crm.models.client.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateService {
    @Autowired
    private CalculateRepository calculateRepository;

    public void add(Calculate calculate) {
        calculateRepository.saveAndFlush(calculate);
    }

    public void delete(Calculate calculate) {
        calculateRepository.delete(calculate);
    }

    public List<Calculate> getAll() {
        return calculateRepository.findAll();
    }

    public Calculate getOne(Long id) {
        return calculateRepository.getOne(id);
    }

    public List<Calculate> getAllOpen() {
        return calculateRepository.getAllOpen();
    }
}

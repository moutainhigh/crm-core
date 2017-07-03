package com.cafe.crm.service_impl.calculateService;

import com.cafe.crm.dao.calculate.CalculateRepository;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.service_abstract.calculateService.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateServiceImpl implements CalculateService {
    @Autowired
    private CalculateRepository calculateRepository;

    public void save(Calculate calculate) {
        calculateRepository.saveAndFlush(calculate);
    }

    public void delete(Calculate calculate) {
        calculateRepository.delete(calculate);
    }

    public List<Calculate> getAll() {
        return calculateRepository.findAll();
    }

    public Calculate getOne(Long id) {
        return calculateRepository.findOne(id);
    }

    public List<Calculate> getAllOpen() {
        return calculateRepository.getAllOpen();
    }
}

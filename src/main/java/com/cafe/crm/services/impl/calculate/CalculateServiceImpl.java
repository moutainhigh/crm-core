package com.cafe.crm.services.impl.calculate;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.repositories.calculate.CalculateRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateServiceImpl implements CalculateService {

	@Autowired
	private CalculateRepository calculateRepository;

	@Override
	public void save(Calculate calculate) {
		calculateRepository.save(calculate);
	}

	@Override
	public void delete(Calculate calculate) {
		calculateRepository.delete(calculate);
	}

	@Override
	public List<Calculate> getAll() {
		return calculateRepository.findAll();
	}

	@Override
	public Calculate getOne(Long id) {
		return calculateRepository.findOne(id);
	}

	@Override
	public List<Calculate> getAllOpen() {
		return calculateRepository.getAllOpen();
	}

	@Override
	public Calculate getAllOpenOnCalculate(Long calculateId) {
		return calculateRepository.getAllOpenOnCalculate(calculateId);
	}

	@Override
	public Calculate findByClientId(Long clientId) {
		return calculateRepository.findByClientId(clientId);
	}

}

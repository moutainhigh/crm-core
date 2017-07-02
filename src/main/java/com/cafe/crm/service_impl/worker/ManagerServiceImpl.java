package com.cafe.crm.service_impl.worker;


import com.cafe.crm.dao.manager.ManagerRepository;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.service_abstract.worker.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	@Override
	public Manager getUserByEmail(String email) {
		return managerRepository.findByEmail(email);
	}

	@Override
	public void save(Manager manager) {
		managerRepository.save(manager);
	}
}

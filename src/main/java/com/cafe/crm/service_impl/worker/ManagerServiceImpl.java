package com.cafe.crm.service_impl.worker;


import com.cafe.crm.dao.manager.ManagerRepository;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.service_abstract.worker_service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	@Override
	public Manager getUserByLogin(@Param("name") String login) {
		return managerRepository.getUserByLogin(login);
	}

	@Override
	public void save(Manager manager) {
		managerRepository.save(manager);
	}
}

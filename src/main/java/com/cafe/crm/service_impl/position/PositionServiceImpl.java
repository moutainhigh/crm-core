package com.cafe.crm.service_impl.position;

import com.cafe.crm.dao.manager.ManagerRepository;
import com.cafe.crm.dao.position.PositionRepository;
import com.cafe.crm.dao.worker.WorkerRepository;
import com.cafe.crm.models.worker.Position;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service_abstract.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Override
	public void addPosition(Position position) {
		positionRepository.saveAndFlush(position);
	}

	@Override
	public List<Position> listAllPosition() {
		return positionRepository.findAll();
	}

	@Override
	public void updatePosition(Position position) {
		positionRepository.saveAndFlush(position);
	}

	@Override
	public void deletePosition(Long id) {
		List<Worker> allWorkerList = workerRepository.findAll();
		Position position = positionRepository.findOne(id);
		for (Worker w : allWorkerList) {
			w.getAllPosition().remove(position);
			workerRepository.saveAndFlush(w);
		}
		positionRepository.delete(id);
	}

	@Override
	public Position findPositionById(Long id) {
		return positionRepository.findOne(id);
	}

	@Override
	public Position findPositionByName(String name) {
		return positionRepository.getPositionByName(name);
	}
}

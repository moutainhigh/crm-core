package com.cafe.crm.services.impl.position;

import com.cafe.crm.models.worker.Position;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.repositories.position.PositionRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private WorkerRepository workerRepository;

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
        for (Worker worker : allWorkerList) {
            worker.getAllPosition().remove(position);
            workerRepository.saveAndFlush(worker);
        }
        positionRepository.delete(id);
    }

    @Override
    public Position findById(Long id) {
        return positionRepository.findOne(id);
    }
}

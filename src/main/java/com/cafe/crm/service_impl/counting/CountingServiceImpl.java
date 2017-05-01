package com.cafe.crm.service_impl.counting;

import com.cafe.crm.dao.ManagerRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service.counting.CountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CountingServiceImpl implements CountingService {


    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private WorkerRepository workerRepository;


    // Запрос по дате (Тестовый вариант)
    @Override
    public void shiftByDate(LocalDate start, LocalDate end) {
        List<Manager> managerList = managerRepository.findAll();
        for (Manager manager : managerList) {
            Set<Shift> allShifts = managerRepository.findByDates(start, end);
            Long shiftSalary = manager.getShiftSalary();
            Integer countId = allShifts.size();
            Long salary = countId * shiftSalary;
            manager.setSalary(salary);
            managerRepository.save(manager);
        }
    }

    // Подсчет количества смен (Тестовый вариант)
    @Override
    public void countShift() {
        List<Worker> workerList = workerRepository.findAll();
        Set<Shift> allShifts = new HashSet<>();
        for (Worker worker : workerList) {
            allShifts = worker.getAllShifts();
            Integer countId = allShifts.size();
            worker.setCountShift(Long.valueOf(countId));
            workerRepository.saveAndFlush(worker);
        }
    }

    // расчет зарплат всех сотрудников(кол-во смен * оклад) (Тестовый вариант)
    @Override
    public void totalSalary() {
        List<Worker> workerList = workerRepository.findAll();
        Long shiftSalary;//оклад
        Long countShift;//количество смен
        Long salary;
        for (Worker worker : workerList) {
            shiftSalary = worker.getShiftSalary();
            countShift = worker.getCountShift();
            salary = shiftSalary * countShift;
            worker.setSalary(salary);
            workerRepository.saveAndFlush(worker);
        }
    }
}






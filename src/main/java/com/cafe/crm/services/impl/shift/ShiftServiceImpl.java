package com.cafe.crm.services.impl.shift;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.shift.ShiftView;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.repositories.goods.GoodsRepository;
import com.cafe.crm.repositories.shift.ShiftRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private TimeManager timeManager;

	@Override
	public void saveAndFlush(Shift shift) {
		shiftRepository.saveAndFlush(shift);
	}


	@Override
	public Shift newShift(long[] box, Double cashBox, Double bankCashBox) {
		Set<Worker> users = new HashSet<>();
		for (int i = 0; i < box.length; i++) {
			Worker worker = workerRepository.getOne(box[i]);
			users.add(worker);
		}
		Shift shift = new Shift(timeManager.getDate(), users, bankCashBox);
		shift.setOpen(true);
		for (Worker worker : users) {
			worker.getAllShifts().add(shift);
		}
		if (cashBox == null) {
			Shift lastShift = shiftRepository.getLast();
			shift.setCashBox(lastShift.getCashBox());
		} else {
			shift.setCashBox(cashBox);
			shift.setBankCashBox(bankCashBox);
		}
		shiftRepository.saveAndFlush(shift);
		return shift;
	}

	@Transactional(readOnly = true)
	@Override
	public Shift findOne(Long L) {
		return shiftRepository.findOne(L);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Worker> findAllUsers() {
		return workerRepository.getAllActiveWorker();
	}

	@Override
	public List<Worker> getWorkers() {    // Рабочие не добавленные в открытую  смену

		List<Worker> workers = workerRepository.getAllActiveWorker();
		workers.removeAll(shiftRepository.getLast().getUsers());
		return workers;
	}

	@Transactional(readOnly = true)
	@Override   // Рабочие добавленные в открытую  смену
	public Set<Worker> getAllActiveWorkers() {
		return shiftRepository.getLast().getUsers();
	}

	@Override
	public void deleteWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		Worker worker = workerRepository.getWorkerByNameForShift(name);
		shift.getUsers().remove(worker);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public void addWorkerFromShift(String name) {
		Shift shift = shiftRepository.getLast();
		Worker worker = workerRepository.getWorkerByNameForShift(name);
		shift.getUsers().add(worker);
		shiftRepository.saveAndFlush(shift);
	}

	@Transactional(readOnly = true)
	@Override
	public Shift getLast() {
		return shiftRepository.getLast();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Shift> findAll() {
		return shiftRepository.findAll();
	}

	@Override
	public void closeShift(Double totalCashBox, Map<Long, Long> workerIdBonusMap, Double allPrice, Double shortage,
						   Double bankKart) {
		GoodsCategory goodsCategory = goodsCategoryService.findByNameIgnoreCase("Зарплата сотрудников");
		Shift shift = shiftRepository.getLast();
		for (Map.Entry<Long, Long> entry : workerIdBonusMap.entrySet()) {
			Worker worker = workerRepository.findOne(entry.getKey());
			Long shiftSalaryWorker = worker.getShiftSalary();// оклад сотрудника
			Long salaryWorker = worker.getSalary();
			salaryWorker = salaryWorker + shiftSalaryWorker;
			worker.setSalary(salaryWorker);
			Long bonusValue = (entry.getValue());
			Long bonus = worker.getBonus();
			bonus = bonus + bonusValue;
			worker.setBonus(bonus);
			Long salaryCost = shiftSalaryWorker + bonusValue;
			Goods goodsSalaryWorker = new Goods(worker.getFirstName() + " " + worker.getLastName(),
					salaryCost, 1,
					goodsCategory, LocalDate.now());
			goodsService.save(goodsSalaryWorker);
			workerRepository.saveAndFlush(worker);
		}
		shift.setBankCashBox(bankKart + shift.getBankCashBox());
		shift.setOpen(false);
		shift.setCashBox(totalCashBox - shortage);
		shift.setProfit(allPrice);
		shiftRepository.saveAndFlush(shift);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<Shift> findByDates(LocalDate start, LocalDate end) {
		return shiftRepository.findByDates(start, end);
	}

	@Override
	public ShiftView createShiftView(Shift shift) {
		Set<Worker> allActiveWorker = shiftService.getAllActiveWorkers();// добавленные воркеры на смену
		Set<Client> clients = shiftService.getLast().getClients(); //клиенты на смене
		List<Calculate> activeCalculate = calculateService.getAllOpen(); //открытые счета
		Set<Calculate> allCalculate = shift.getAllCalculate(); //все счета за смену
		Double cashBox = shift.getCashBox(); //касса смены без учета расходов
		Double bankCashBox = shift.getBankCashBox(); //деньги на банковской карте
		Double totalCashBox = 0D; //итоговая касса
		Long salaryWorker = 0L; //зп сотрудников
		Double card = 0D; //оплата по клубным картам
		Double allPrice = 0D; //прибыль грязными
		for (Worker worker : allActiveWorker) {
			salaryWorker = salaryWorker + worker.getShiftSalary();
		}
		for (Client client : clients) {
			card = card + client.getPayWithCard();
			allPrice = allPrice + client.getAllPrice();
		}
		LocalDate shiftDate = shift.getDateShift();
		Set<Goods> goodsSet = goodsRepository.findByDateAndVisibleTrue(shiftDate);
		goodsSet.removeAll(goodsRepository.findByDateAndCategoryNameAndVisibleTrue(shiftDate, "Зарплата сотрудников"));
		Double otherCosts = 0D;
		for (Goods goods : goodsSet) {
			otherCosts = otherCosts + (goods.getPrice() * goods.getQuantity());
		}
		totalCashBox = (cashBox + allPrice) - (salaryWorker + otherCosts);
		return new ShiftView(allActiveWorker, clients, activeCalculate, allCalculate,
				cashBox, totalCashBox, salaryWorker, card, allPrice, shiftDate, otherCosts, bankCashBox);
	}

	@Override
	public Shift findByDateShift(LocalDate date) {
		return shiftRepository.findByDateShift(date);
	}

}

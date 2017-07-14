package com.cafe.crm.services.impl.worker;

import com.cafe.crm.models.worker.*;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.manager.ManagerRepository;
import com.cafe.crm.repositories.position.PositionRepository;
import com.cafe.crm.repositories.role.RoleRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private BossRepository bossRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	@Override
	public List<Worker> listAllWorker() {
		return workerRepository.getAllActiveWorker();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Manager> listAllManager() {
		return managerRepository.getAllActiveManager();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Boss> listAllBoss() {
		return bossRepository.getAllActiveBoss();
	}

	@Override
	public void addWorker(Worker worker) {
		worker.setEnabled(true);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void addManager(Manager manager) {
		String hashedPassword = passwordEncoder.encode(manager.getPassword());
		manager.setPassword(hashedPassword);
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.getRoleByName("MANAGER"));
		List<Position> activePosition = manager.getAllPosition();
		manager.setAllPosition(activePosition);
		manager.setRoles(roles);
		manager.setEnabled(true);
		managerRepository.saveAndFlush(manager);
	}

	@Override
	public void addBoss(Boss boss) {
		String hashedPassword = passwordEncoder.encode(boss.getPassword());
		boss.setPassword(hashedPassword);
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.getRoleByName("BOSS"));
		List<Position> activePosition = boss.getAllPosition();
		boss.setAllPosition(activePosition);
		boss.setRoles(roles);
		boss.setEnabled(true);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void editWorker(Worker worker, Long adminId, Long bossId, String password, String submitPassword) {
		List<Position> active = worker.getAllPosition();
		if (adminId == null && bossId == null) {
			workerRepository.saveAndFlush(worker);
		} else if (password.equals(submitPassword)) {
			if (adminId != null && bossId == null) {
				Set<Role> rolesAdmin = new HashSet<>();
				rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
				active.add(positionRepository.findOne(adminId));
				worker.setAllPosition(active);
				Manager manager = new Manager(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
						worker.getAllPosition(), worker.getShiftSalary(), worker.getSalary(),
						worker.getPhone(), rolesAdmin, "admin", true);
				worker.setEnabled(false);
				managerRepository.saveAndFlush(manager);
				workerRepository.saveAndFlush(worker);
			} else if (adminId == null && bossId != null) {
				Set<Role> rolesBoss = new HashSet<>();
				rolesBoss.add(roleRepository.getRoleByName("BOSS"));
				active.add(positionRepository.findOne(bossId));
				worker.setAllPosition(active);
				Boss boss = new Boss(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
						worker.getAllPosition(), 0L, 0L,
						worker.getPhone(), rolesBoss, "boss", true);
				worker.setEnabled(false);
				bossRepository.saveAndFlush(boss);
				workerRepository.saveAndFlush(worker);
			}
			if (adminId != null && bossId != null) {
				Set<Role> rolesBoss = new HashSet<>();
				rolesBoss.add(roleRepository.getRoleByName("BOSS"));
				active.add(positionRepository.findOne(bossId));
				active.add(positionRepository.findOne(adminId));
				worker.setAllPosition(active);
				Boss boss = new Boss(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
						worker.getAllPosition(), 0L, 0L,
						worker.getPhone(), rolesBoss, "boss", true);
				worker.setEnabled(false);
				bossRepository.saveAndFlush(boss);
				workerRepository.saveAndFlush(worker);
			}
		}
	}

	@Override
	public void editManager(Manager manager, Long adminId, Long bossId) {
		if (adminId != null && bossId != null) {
			Set<Role> rolesBoss = new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> active = manager.getAllPosition();
			active.add(positionRepository.findOne(adminId));
			active.add(positionRepository.findOne(bossId));
			manager.setAllPosition(active);
			Boss boss = new Boss(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
					manager.getAllPosition(), 0L, 0L,
					manager.getPhone(), rolesBoss, "boss", true);
			manager.setEnabled(false);
			bossRepository.saveAndFlush(boss);
			managerRepository.saveAndFlush(manager);
		} else if (adminId != null) {
			List<Position> active = manager.getAllPosition();
			active.add(positionRepository.findOne(adminId));
			manager.setAllPosition(active);
			managerRepository.saveAndFlush(manager);
		} else if (bossId != null) {
			Set<Role> rolesBoss = new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> active = manager.getAllPosition();
			active.add(positionRepository.findOne(bossId));
			manager.setAllPosition(active);
			Boss boss = new Boss(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
					manager.getAllPosition(), 0L, 0L,
					manager.getPhone(), rolesBoss, "boss", true);
			manager.setEnabled(false);
			bossRepository.saveAndFlush(boss);
			managerRepository.saveAndFlush(manager);
		} else {
			Worker worker = new Worker(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
					manager.getAllPosition(), manager.getShiftSalary(), manager.getSalary(),
					"worker", true);
			manager.setEnabled(false);
			managerRepository.saveAndFlush(manager);
			workerRepository.saveAndFlush(worker);
		}

	}

	@Override
	public void editBoss(Boss boss, Long bossId, Long adminId, Long shiftSalary) {
		List<Position> active = boss.getAllPosition();
		boss.setAllPosition(active);
		if (bossId == null && adminId == null) {
			Worker worker = new Worker(boss.getFirstName(), boss.getLastName(), boss.getEmail(), boss.getPhone(),
					boss.getAllPosition(), 0L, boss.getSalary(),
					"worker", true);
			boss.setEnabled(false);
			workerRepository.saveAndFlush(worker);
			bossRepository.saveAndFlush(boss);
		} else if (bossId == null && adminId != null) {
			Set<Role> rolesAdmin = new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
			active.add(positionRepository.findOne(adminId));
			Manager manager = new Manager(boss.getFirstName(), boss.getLastName(), boss.getEmail(), boss.getPhone(),
					boss.getAllPosition(), shiftSalary, 0L, boss.getPhone(),
					rolesAdmin, "admin", true);
			boss.setEnabled(false);
			managerRepository.saveAndFlush(manager);
			bossRepository.saveAndFlush(boss);
		} else if (bossId != null && adminId == null) {
			List<Position> activePosBoss = boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setAllPosition(activePosBoss);
			bossRepository.saveAndFlush(boss);
		} else {
			List<Position> activePosBoss = boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(adminId));
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setAllPosition(activePosBoss);
			bossRepository.saveAndFlush(boss);
		}
	}

	@Override
	public void deleteWorker(Worker worker) {
		worker.setEnabled(false);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void castWorkerToManager(Worker worker, String password, Long adminPositionId) {
		String hashedPassword = passwordEncoder.encode(password);
		Set<Role> rolesAdmin = new HashSet<>();
		rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
		List<Position> active = worker.getAllPosition();
		active.add(positionRepository.findOne(adminPositionId));
		worker.setAllPosition(active);
		Manager manager = new Manager(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
				worker.getAllPosition(), worker.getShiftSalary(), worker.getSalary(), hashedPassword,
				rolesAdmin, "admin", true);
		worker.setEnabled(false);
		managerRepository.saveAndFlush(manager);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void castWorkerToBoss(Worker worker, String password, Long bossPositionId) {
		String hashedPassword = passwordEncoder.encode(password);
		Set<Role> rolesBoss = new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active = worker.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		worker.setAllPosition(active);
		Boss boss = new Boss(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
				worker.getAllPosition(), worker.getShiftSalary(), worker.getSalary(), hashedPassword,
				rolesBoss, "boss", true);
		worker.setEnabled(false);
		bossRepository.saveAndFlush(boss);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void castManagerToBoss(Manager manager, Long bossPositionId) {
		Set<Role> rolesBoss = new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active = manager.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		manager.setAllPosition(active);
		Boss boss = new Boss(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
				manager.getAllPosition(), manager.getShiftSalary(), manager.getSalary(),
				manager.getPhone(), rolesBoss, "boss", true);
		boss.setEnabled(true);
		manager.setEnabled(false);
		bossRepository.saveAndFlush(boss);
		managerRepository.saveAndFlush(manager);
	}

	@Transactional(readOnly = true)
	@Override
	public Worker findWorkerById(Long id) {
		return workerRepository.findOne(id);
	}

	public String parsePhoneNumber(String phoneNumber) {
		String result = phoneNumber.replaceAll("[^0-9]+", "");
		int startIndex = result.length() > 10 ? result.length() - 10 : 0;
		int endIndex = result.length();
		return result.substring(startIndex, endIndex);
	}

}

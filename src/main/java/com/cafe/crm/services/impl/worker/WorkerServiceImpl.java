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
		if (adminId == null && bossId == null) {
			workerRepository.saveAndFlush(worker);
		} else if (password.equals(submitPassword)) {
			if (adminId != null && bossId == null) {
				castWorkerToManager(worker, password, adminId);
			} else if (adminId == null && bossId != null) {
				castWorkerToBoss(worker, password, bossId);
			}
			if (adminId != null && bossId != null) {
				castWorkerToBoss(worker, password, bossId);
			}
		}
	}

	@Override
	public void editManager(Manager manager, Long adminId, Long bossId) {
		if (adminId != null && bossId != null) {
			castManagerToBoss(manager, bossId);
		} else if (adminId != null) {
			Set<Role> adminRoles = new HashSet<>();
			List<Position> active = manager.getAllPosition();
			active.add(positionRepository.findOne(adminId));
			adminRoles.add(roleRepository.getRoleByName("MANAGER"));
			manager.setRoles(adminRoles);
			managerRepository.saveAndFlush(manager);
		} else if (bossId != null) {
			castManagerToBoss(manager, bossId);
		} else {
			castManagerToWorker(manager);
		}
	}

	@Override
	public void editBoss(Boss boss, Long bossId, Long adminId, Long shiftSalary) {
		List<Position> active = boss.getAllPosition();
		boss.setAllPosition(active);
		if (bossId == null && adminId == null) {
			castBossToWorker(boss);
		} else if (bossId == null && adminId != null) {
			castBossToManager(boss, adminId ,shiftSalary);
		} else if (bossId != null && adminId == null) {
			Set<Role> rolesBoss = new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> activePosBoss = boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setRoles(rolesBoss);
			boss.setAllPosition(activePosBoss);
			bossRepository.saveAndFlush(boss);
		} else {
			Set<Role> rolesBoss = new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> activePosBoss = boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(adminId));
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setRoles(rolesBoss);
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
		worker.setEmail("");
		worker.setPhone("");
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
		worker.setEmail("");
		worker.setPhone("");
		workerRepository.saveAndFlush(worker);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void castManagerToBoss(Manager manager, Long bossPositionId) {
		Set<Role> rolesBoss = new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active = manager.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		manager.setAllPosition(active);
		Boss boss = new Boss(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
				active, 0L, 0L,
				manager.getPassword(), rolesBoss, "boss", true);
		manager.setEmail("");
		manager.setPhone("");
		manager.setEnabled(false);
		managerRepository.saveAndFlush(manager);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void castManagerToWorker(Manager manager) {
		List<Position> active = manager.getAllPosition();
		manager.setAllPosition(active);
		Worker worker = new Worker(manager.getFirstName(), manager.getLastName(), "", "",
				manager.getAllPosition(), manager.getShiftSalary(), manager.getSalary(), "worker", true);
		manager.setEnabled(true);
		manager.setEmail("");
		manager.setPhone("");
		manager.setEnabled(false);
		managerRepository.saveAndFlush(manager);
		workerRepository.saveAndFlush(worker);
	}


	@Override
	public void castBossToWorker(Boss boss) {
		Worker worker = new Worker(boss.getFirstName(), boss.getLastName(), "", "",
				boss.getAllPosition(), 0L, 0L, "worker", true);
		boss.setEnabled(true);
		boss.setEmail("");
		boss.setPhone("");
		workerRepository.saveAndFlush(worker);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void castBossToManager(Boss boss, Long adminPositionId , Long shiftSalary) {
		List<Position> active = boss.getAllPosition();
		active.add(positionRepository.findOne(adminPositionId));
		Set<Role> rolesAdmin = new HashSet<>();
		rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
		Manager manager = new Manager(boss.getFirstName(), boss.getLastName(), boss.getEmail(), boss.getPhone(),
				active, shiftSalary, 0L, boss.getPassword(),
				rolesAdmin, "admin", true);
		boss.setEnabled(false);
		boss.setEmail("");
		boss.setPhone("");
		managerRepository.saveAndFlush(manager);
		bossRepository.saveAndFlush(boss);
	}

	@Transactional(readOnly = true)
	@Override
	public Worker findWorkerById(Long id) {
		return workerRepository.findOne(id);
	}

	@Override
	public String parsePhoneNumber(String phoneNumber) {
		String result = phoneNumber.replaceAll("[^0-9]+", "");
		int startIndex = result.length() > 10 ? result.length() - 10 : 0;
		int endIndex = result.length();
		return result.substring(startIndex, endIndex);
	}

}

package com.cafe.crm.service_impl.worker;

import com.cafe.crm.dao.boss.BossRepository;
import com.cafe.crm.dao.manager.ManagerRepository;
import com.cafe.crm.dao.position.PositionRepository;
import com.cafe.crm.dao.role.RoleRepository;
import com.cafe.crm.dao.worker.WorkerRepository;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.*;

import com.cafe.crm.service_abstract.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Override
	public List<Worker> listAllWorker() {
		return workerRepository.findAll();
	}

	@Override
	public List<Manager> listAllManager() {
		return managerRepository.findAll();
	}

	@Override
	public List<Boss> listAllBoss() {
		return bossRepository.findAll();
	}

	@Override
	public void addWorker(Worker worker) {
		worker.setEnabled(true);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void addManager(Manager manager) {
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
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.getRoleByName("BOSS"));
		List<Position> activePosition = boss.getAllPosition();
		boss.setAllPosition(activePosition);
		boss.setRoles(roles);
		boss.setEnabled(true);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void editWorker(Worker worker, Long adminId, Long bossId, String password) {
		List<Position> active = worker.getAllPosition();
		if (adminId == null && bossId == null) {
			workerRepository.saveAndFlush(worker);
		} else if (adminId != null && bossId == null) {
			Set<Role> rolesAdmin = new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
			active.add(positionRepository.findOne(adminId));
			worker.setAllPosition(active);
			Manager manager = new Manager(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
					worker.getAllPosition(), worker.getShiftSalary(), worker.getCountShift(), worker.getSalary(), password,
					rolesAdmin, "admin", true);
			worker.setEnabled(false);
			managerRepository.saveAndFlush(manager);
			workerRepository.saveAndFlush(worker);
		} else if (adminId == null && bossId != null) {
			Set<Role> rolesBoss = new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			active.add(positionRepository.findOne(bossId));
			worker.setAllPosition(active);
			Boss boss = new Boss(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
					worker.getAllPosition(), worker.getShiftSalary(), worker.getCountShift(), worker.getSalary(), password,
					rolesBoss, "boss", true);
			worker.setEnabled(false);
			bossRepository.saveAndFlush(boss);
			workerRepository.saveAndFlush(worker);
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
					manager.getAllPosition(), manager.getShiftSalary(), manager.getCountShift(), manager.getSalary(),
					manager.getPassword(),
					rolesBoss, "boss", true);
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
					manager.getAllPosition(), manager.getShiftSalary(), manager.getCountShift(), manager.getSalary(),
					manager.getPassword(),
					rolesBoss, "boss", true);
			manager.setEnabled(false);
			bossRepository.saveAndFlush(boss);
			managerRepository.saveAndFlush(manager);
		} else {
			Worker worker = new Worker(manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(),
					manager.getAllPosition(), manager.getShiftSalary(), manager.getCountShift(), manager.getSalary(),
					"worker", true);
			manager.setEnabled(false);
			managerRepository.saveAndFlush(manager);
			workerRepository.saveAndFlush(worker);
		}

	}

	@Override
	public void editBoss(Boss boss, Long bossId, Long adminId) {
		List<Position> active = boss.getAllPosition();
		boss.setAllPosition(active);
		if (bossId == null && adminId == null) {
			Worker worker = new Worker(boss.getFirstName(), boss.getLastName(), boss.getEmail(), boss.getPhone(),
					boss.getAllPosition(), boss.getShiftSalary(), boss.getCountShift(), boss.getSalary(),
					"worker", true);
			boss.setEnabled(false);
			workerRepository.saveAndFlush(worker);
			bossRepository.saveAndFlush(boss);
		} else if (bossId == null) {
			Set<Role> rolesAdmin = new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
			Manager manager = new Manager(boss.getFirstName(), boss.getLastName(), boss.getEmail(), boss.getPhone(),
					boss.getAllPosition(), boss.getShiftSalary(), boss.getCountShift(), boss.getSalary(), boss.getPassword(),
					rolesAdmin, "admin", true);
			boss.setEnabled(false);
			managerRepository.saveAndFlush(manager);
			bossRepository.saveAndFlush(boss);
		} else {
			Set<Role> rolesAdmin = new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("BOSS"));
			List<Position> activePosBoss = boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setAllPosition(activePosBoss);
			boss.setRoles(rolesAdmin);
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
		Set<Role> rolesAdmin = new HashSet<>();
		rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
		List<Position> active = worker.getAllPosition();
		active.add(positionRepository.findOne(adminPositionId));
		worker.setAllPosition(active);
		Manager manager = new Manager(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
				worker.getAllPosition(), worker.getShiftSalary(), worker.getCountShift(), worker.getSalary(), password,
				rolesAdmin, "admin", true);
		worker.setEnabled(false);
		managerRepository.saveAndFlush(manager);
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void castWorkerToBoss(Worker worker, String password, Long bossPositionId) {
		Set<Role> rolesBoss = new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active = worker.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		worker.setAllPosition(active);
		Boss boss = new Boss(worker.getFirstName(), worker.getLastName(), worker.getEmail(), worker.getPhone(),
				worker.getAllPosition(), worker.getShiftSalary(), worker.getCountShift(), worker.getSalary(), password,
				rolesBoss, "Boss", true);
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
				manager.getAllPosition(), manager.getShiftSalary(), manager.getCountShift(), manager.getSalary(),
				manager.getPassword(), rolesBoss, "boss", true);
		boss.setEnabled(true);
		manager.setEnabled(false);
		bossRepository.saveAndFlush(boss);
		managerRepository.saveAndFlush(manager);
	}

	@Override
	public Worker findWorkerById(Long id) {
		return workerRepository.findOne(id);
	}
}






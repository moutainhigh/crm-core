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
		workerRepository.saveAndFlush(worker);
	}

	@Override
	public void addManager(Manager manager) {
		Set<Role> roles=new HashSet<>();
		roles.add(roleRepository.getRoleByName("MANAGER"));
		if (positionRepository.getPositionByName("Администратор")==null){
			Position adminPosition=new Position("Администратор");
			positionRepository.saveAndFlush(adminPosition);
			List<Position> activePosition=manager.getAllPosition();
			activePosition.add(adminPosition);
			manager.setAllPosition(activePosition);
			manager.setRoles(roles);
			managerRepository.saveAndFlush(manager);
		}else {
			manager.setRoles(roles);
			managerRepository.saveAndFlush(manager);
		}
	}

	@Override
	public void addBoss(Boss boss) {
		Set<Role> roles=new HashSet<>();
		roles.add(roleRepository.getRoleByName("BOSS"));
		if (positionRepository.getPositionByName("Владелец")==null){
			Position bossPosition=new Position("Владелец");
			positionRepository.saveAndFlush(bossPosition);
			List<Position> activePosition=boss.getAllPosition();
			activePosition.add(bossPosition);
			boss.setAllPosition(activePosition);
			boss.setRoles(roles);
			bossRepository.saveAndFlush(boss);
		}else {
			boss.setRoles(roles);
			bossRepository.saveAndFlush(boss);
		}
	}

	@Override
	public void editWorker(Worker worker, Long adminId, Long bossId,String password) {
		List<Position> active=worker.getAllPosition();
		if (adminId==null && bossId==null){
			workerRepository.saveAndFlush(worker);
		}else if (adminId!=null && bossId==null){
			Set<Role> rolesAdmin=new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
			active.add(positionRepository.findOne(adminId));
			worker.setAllPosition(active);
			Manager manager=new Manager();
			manager.setFirstName(worker.getFirstName());
			manager.setLastName(worker.getLastName());
			manager.setEmail(worker.getEmail());
			manager.setPhone(worker.getPhone());
			manager.setShiftSalary(worker.getShiftSalary());
			manager.setAllPosition(worker.getAllPosition());
			manager.setPassword(password);
			manager.setCountShift(worker.getCountShift());
			manager.setSalary(worker.getSalary());
			manager.setActionForm("Admin");
			manager.setRoles(rolesAdmin);
			managerRepository.saveAndFlush(manager);
			workerRepository.delete(worker);
		}else if (adminId == null && bossId!=null){
			Set<Role> rolesBoss=new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			active.add(positionRepository.findOne(bossId));
			worker.setAllPosition(active);
			Boss boss=new Boss();
			boss.setFirstName(worker.getFirstName());
			boss.setLastName(worker.getLastName());
			boss.setEmail(worker.getEmail());
			boss.setPhone(worker.getPhone());
			boss.setShiftSalary(worker.getShiftSalary());
			boss.setAllPosition(worker.getAllPosition());
			boss.setPassword(password);
			boss.setCountShift(worker.getCountShift());
			boss.setSalary(worker.getSalary());
			boss.setActionForm("Boss");
			boss.setRoles(rolesBoss);
			bossRepository.saveAndFlush(boss);
			workerRepository.delete(worker);
		}
	}

	@Override
	public void editManager(Manager manager, Long adminId,Long bossId) {

		if (adminId!=null && bossId!=null){
			Set<Role> rolesBoss=new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> active=manager.getAllPosition();
			active.add(positionRepository.findOne(adminId));
			active.add(positionRepository.findOne(bossId));
			manager.setAllPosition(active);
			Boss boss=new Boss();
			boss.setFirstName(manager.getFirstName());
			boss.setLastName(manager.getLastName());
			boss.setEmail(manager.getEmail());
			boss.setPhone(manager.getPhone());
			boss.setShiftSalary(manager.getShiftSalary());
			boss.setAllPosition(manager.getAllPosition());
			boss.setPassword(manager.getPassword());
			boss.setActionForm("Boss");
			boss.setCountShift(manager.getCountShift());
			boss.setSalary(manager.getSalary());
			boss.setRoles(rolesBoss);
			bossRepository.saveAndFlush(boss);
			managerRepository.delete(manager);
		}else if (adminId!=null) {
			List<Position> active=manager.getAllPosition();
			active.add(positionRepository.findOne(adminId));
			manager.setAllPosition(active);
			managerRepository.saveAndFlush(manager);
		} else if (bossId!=null){
			Set<Role> rolesBoss=new HashSet<>();
			rolesBoss.add(roleRepository.getRoleByName("BOSS"));
			List<Position> active=manager.getAllPosition();
			active.add(positionRepository.findOne(bossId));
			manager.setAllPosition(active);
			Boss boss=new Boss();
			boss.setFirstName(manager.getFirstName());
			boss.setLastName(manager.getLastName());
			boss.setEmail(manager.getEmail());
			boss.setPhone(manager.getPhone());
			boss.setShiftSalary(manager.getShiftSalary());
			boss.setAllPosition(manager.getAllPosition());
			boss.setPassword(manager.getPassword());
			boss.setActionForm("Boss");
			boss.setCountShift(manager.getCountShift());
			boss.setSalary(manager.getSalary());
			boss.setRoles(rolesBoss);
			bossRepository.saveAndFlush(boss);
			managerRepository.delete(manager);
		}else {
			Worker worker=new Worker();
			worker.setFirstName(manager.getFirstName());
			worker.setLastName(manager.getLastName());
			worker.setEmail(manager.getEmail());
			worker.setPhone(manager.getPhone());
			worker.setShiftSalary(manager.getShiftSalary());
			worker.setAllPosition(manager.getAllPosition());
			worker.setActionForm("Worker");
			worker.setCountShift(manager.getCountShift());
			worker.setSalary(manager.getSalary());
			managerRepository.delete(manager);
			workerRepository.saveAndFlush(worker);
		}

	}
	@Override
	public void editBoss(Boss boss, Long bossId,Long adminId) {
		List<Position> active=boss.getAllPosition();
		boss.setAllPosition(active);
		if (bossId==null && adminId==null){
			Worker worker=new Worker();
			worker.setFirstName(boss.getFirstName());
			worker.setLastName(boss.getLastName());
			worker.setEmail(boss.getEmail());
			worker.setPhone(boss.getPhone());
			worker.setShiftSalary(boss.getShiftSalary());
			worker.setAllPosition(boss.getAllPosition());
			worker.setCountShift(boss.getCountShift());
			worker.setSalary(boss.getSalary());
			worker.setActionForm("Worker");
			workerRepository.saveAndFlush(worker);
			bossRepository.delete(boss);
		}else if (bossId == null) {
			Set<Role> rolesAdmin=new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
			Manager manager=new Manager();
			manager.setFirstName(boss.getFirstName());
			manager.setLastName(boss.getLastName());
			manager.setEmail(boss.getEmail());
			manager.setPhone(boss.getPhone());
			manager.setShiftSalary(boss.getShiftSalary());
			manager.setAllPosition(boss.getAllPosition());
			manager.setCountShift(boss.getCountShift());
			manager.setSalary(boss.getSalary());
			manager.setPassword(boss.getPassword());
			manager.setActionForm("Admin");
			manager.setRoles(rolesAdmin);
			managerRepository.saveAndFlush(manager);
			bossRepository.delete(boss);
		} else {
			Set<Role> rolesAdmin=new HashSet<>();
			rolesAdmin.add(roleRepository.getRoleByName("BOSS"));
			List<Position> activePosBoss=boss.getAllPosition();
			activePosBoss.add(positionRepository.findOne(bossId));
			boss.setAllPosition(activePosBoss);
			boss.setRoles(rolesAdmin);
			bossRepository.saveAndFlush(boss);
		}
	}

	@Override
	public void deleteWorker(Worker worker) {
		workerRepository.delete(worker);
	}

	@Override
	public void castWorkerToManager(Worker worker,String password,Long adminPositionId) {
		Set<Role> rolesAdmin=new HashSet<>();
		rolesAdmin.add(roleRepository.getRoleByName("MANAGER"));
		List<Position> active=worker.getAllPosition();
		active.add(positionRepository.findOne(adminPositionId));
		worker.setAllPosition(active);
		Manager manager=new Manager();
		manager.setFirstName(worker.getFirstName());
		manager.setLastName(worker.getLastName());
		manager.setEmail(worker.getEmail());
		manager.setPhone(worker.getPhone());
		manager.setShiftSalary(worker.getShiftSalary());
		manager.setAllPosition(worker.getAllPosition());
		manager.setPassword(password);
		manager.setCountShift(worker.getCountShift());
		manager.setSalary(worker.getSalary());
		manager.setActionForm("Admin");
		manager.setRoles(rolesAdmin);
		managerRepository.saveAndFlush(manager);
	}

	@Override
	public void castWorkerToBoss(Worker worker, String password,Long bossPositionId) {
		Set<Role> rolesBoss=new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active=worker.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		worker.setAllPosition(active);
		Boss boss=new Boss();
		boss.setFirstName(worker.getFirstName());
		boss.setLastName(worker.getLastName());
		boss.setEmail(worker.getEmail());
		boss.setPhone(worker.getPhone());
		boss.setShiftSalary(worker.getShiftSalary());
		boss.setAllPosition(worker.getAllPosition());
		boss.setPassword(password);
		boss.setCountShift(worker.getCountShift());
		boss.setSalary(worker.getSalary());
		boss.setActionForm("Boss");
		boss.setRoles(rolesBoss);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public void castManagerToBoss(Manager manager, Long bossPositionId) {
		Set<Role> rolesBoss=new HashSet<>();
		rolesBoss.add(roleRepository.getRoleByName("BOSS"));
		List<Position> active=manager.getAllPosition();
		active.add(positionRepository.findOne(bossPositionId));
		manager.setAllPosition(active);
		Boss boss=new Boss();
		boss.setFirstName(manager.getFirstName());
		boss.setLastName(manager.getLastName());
		boss.setEmail(manager.getEmail());
		boss.setPhone(manager.getPhone());
		boss.setShiftSalary(manager.getShiftSalary());
		boss.setAllPosition(manager.getAllPosition());
		boss.setPassword(manager.getPassword());
		boss.setCountShift(manager.getCountShift());
		boss.setSalary(manager.getSalary());
		boss.setActionForm("Boss");
		boss.setRoles(rolesBoss);
		bossRepository.saveAndFlush(boss);
	}

	@Override
	public Worker findWorkerById(Long id) {
		return workerRepository.findOne(id);
	}


}






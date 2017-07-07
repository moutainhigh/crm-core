package com.cafe.crm.configs.init;

import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.*;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.manager.ManagerRepository;
import com.cafe.crm.repositories.role.RoleRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class InitWorker {

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private BossRepository bossRepository;

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public void init() {

		Position workerPos = new Position("Кальянщик");
		Position adminPos = new Position("Администратор");
		Position bossPos = new Position("Владелец");
		Position workerPos2 = new Position("Чайница");

		List<Position> workerPosList = new ArrayList<>();
		List<Position> adminPosList = new ArrayList<>();
		List<Position> bossPosList = new ArrayList<>();


		workerPosList.add(workerPos);
		workerPosList.add(workerPos2);
		adminPosList.add(adminPos);
		bossPosList.add(bossPos);

		Role roleBoss = new Role();
		roleBoss.setName("BOSS");
		roleRepository.saveAndFlush(roleBoss);

		Role roleAdmin = new Role();
		roleAdmin.setName("MANAGER");
		roleRepository.saveAndFlush(roleAdmin);


		Set<Shift> test = new HashSet<>();
		Set<Shift> test2 = new HashSet<>();
		Set<Shift> test3 = new HashSet<>();

		Worker worker = new Worker();
		worker.setFirstName("Max");
		worker.setLastName("Worker");
		worker.setEmail("Smith@gmail.com");
		worker.setPhone(9123456789L);
		worker.setAllPosition(workerPosList);
		worker.setShiftSalary(1000L);
		worker.setCountShift(2L);
		worker.setSalary(2000L);
		worker.setActionForm("worker");
		worker.setAllShifts(test);
		worker.setEnabled(true);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String adminPassword = "manager";
		String hashedAdminPassword = passwordEncoder.encode(adminPassword);

		Manager manager = new Manager();
		manager.setPassword(hashedAdminPassword);
		manager.setFirstName("Anna");
		manager.setLastName("Jons");
		manager.setEmail("manager@mail.ru");
		manager.setPhone(9233456789L);
		manager.setAllPosition(adminPosList);
		manager.setShiftSalary(1000L);
		manager.setCountShift(2L);
		manager.setSalary(2000L);
		manager.setActionForm("admin");
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(roleAdmin);
		manager.setRoles(adminRoles);
		manager.setAllShifts(test2);
		manager.setEnabled(true);

		String bossPassword = "boss";
		String hashedBossPassword = passwordEncoder.encode(bossPassword);

		Boss boss = new Boss();
		boss.setPassword(hashedBossPassword);
		boss.setFirstName("Martin");
		boss.setLastName("Set");
		boss.setEmail("boss@mail.ru");
		boss.setPhone(9123456789L);
		boss.setAllPosition(bossPosList);
		boss.setShiftSalary(1000L);
		boss.setCountShift(2L);
		boss.setSalary(2000L);
		boss.setActionForm("boss");
		Set<Role> bossRoles = new HashSet<>();
		bossRoles.add(roleBoss);
		boss.setRoles(bossRoles);
		boss.setAllShifts(test3);
		boss.setEnabled(true);


		workerRepository.saveAndFlush(worker);
		managerRepository.saveAndFlush(manager);
		bossRepository.saveAndFlush(boss);
	}
}

package com.cafe.crm.configs.init;

import com.cafe.crm.models.board.Board;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.*;
import com.cafe.crm.repositories.board.BoardRepository;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.goods.GoodsCategoryRepository;
import com.cafe.crm.repositories.manager.ManagerRepository;
import com.cafe.crm.repositories.role.RoleRepository;
import com.cafe.crm.repositories.worker.WorkerRepository;
import com.cafe.crm.services.interfaces.board.BoardService;
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
	private ManagerRepository managerRepository;

	@Autowired
	private BossRepository bossRepository;

	@Autowired
	private RoleRepository roleRepository;


	@PostConstruct
	public void init() {

		Position adminPos = new Position("Администратор");
		Position bossPos = new Position("Владелец");

		List<Position> adminPosList = new ArrayList<>();
		List<Position> bossPosList = new ArrayList<>();


		adminPosList.add(adminPos);
		bossPosList.add(bossPos);

		Role roleBoss = new Role();
		roleBoss.setName("BOSS");
		roleRepository.saveAndFlush(roleBoss);

		Role roleAdmin = new Role();
		roleAdmin.setName("MANAGER");
		roleRepository.saveAndFlush(roleAdmin);

		Set<Shift> test2 = new HashSet<>();
		Set<Shift> test3 = new HashSet<>();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String adminPassword = "manager";
		String hashedAdminPassword = passwordEncoder.encode(adminPassword);

		Manager manager = new Manager();
		manager.setPassword(hashedAdminPassword);
		manager.setFirstName("Anna");
		manager.setLastName("Jons");
		manager.setEmail("manager@mail.ru");
		manager.setPhone("9233456789");
		manager.setAllPosition(adminPosList);
		manager.setShiftSalary(0L);
		manager.setSalary(0L);
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
		boss.setFirstName("Герман");
		boss.setLastName("Севостьянов");
		boss.setEmail("boss@mail.ru");
		boss.setPhone("9123456789");
		boss.setAllPosition(bossPosList);
		boss.setShiftSalary(0L);
		boss.setSalary(0L);
		boss.setActionForm("boss");
		Set<Role> bossRoles = new HashSet<>();
		bossRoles.add(roleBoss);
		boss.setRoles(bossRoles);
		boss.setAllShifts(test3);
		boss.setEnabled(true);


		managerRepository.saveAndFlush(manager);
		bossRepository.saveAndFlush(boss);

		Board board = new Board();
		board.setName("Стол");

	}
}

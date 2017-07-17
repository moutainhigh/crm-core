package com.cafe.crm.configs.init;

import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Position;
import com.cafe.crm.models.worker.Role;
import com.cafe.crm.repositories.boss.BossRepository;
import com.cafe.crm.repositories.role.RoleRepository;
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
    private BossRepository bossRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostConstruct
    public void init() {

        Position adminPos = new Position("Администратор");
        Position bossPos = new Position("Владелец");

        List<Position> bossPosList = new ArrayList<>();


        bossPosList.add(bossPos);
        bossPosList.add(adminPos);

        Role roleBoss = new Role();
        roleBoss.setName("BOSS");
        roleRepository.saveAndFlush(roleBoss);

        Role roleAdmin = new Role();
        roleAdmin.setName("MANAGER");
        roleRepository.saveAndFlush(roleAdmin);

        Set<Shift> test3 = new HashSet<>();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        String bossPassword = "admin";
        String hashedBossPassword = passwordEncoder.encode(bossPassword);

        Boss boss = new Boss();
        boss.setPassword(hashedBossPassword);
        boss.setFirstName("Герман");
        boss.setLastName("Севостьянов");
        boss.setEmail("admin@admin.ru");
        boss.setPhone("0000000000");
        boss.setAllPosition(bossPosList);
        boss.setShiftSalary(0L);
        boss.setSalary(0L);
        boss.setActionForm("boss");
        Set<Role> bossRoles = new HashSet<>();
        bossRoles.add(roleBoss);
        bossRoles.add(roleAdmin);
        boss.setRoles(bossRoles);
        boss.setAllShifts(test3);
        boss.setEnabled(true);


        bossRepository.saveAndFlush(boss);
    }
}

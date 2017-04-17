package BackEnd.initMet;


import BackEnd.dao.RoleDao;
import BackEnd.dao.UserDao;
import BackEnd.models.Role;
import BackEnd.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Test {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    public void init() {
        Role roleAdmin = new Role();
        roleAdmin.setName("BOSS");
        roleDao.saveAndFlush(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("MANAGER");
        roleDao.saveAndFlush(roleUser);

        User admin = new User();
        admin.setLogin("admin1");
        admin.setPassword("admin1");

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleUser);
        adminRoles.add(roleAdmin);
        admin.setRoles(adminRoles);

        userDao.saveAndFlush(admin);

        User user = new User();
        user.setLogin("manager1");
        user.setPassword("manager1");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        userDao.saveAndFlush(user);
    }
}

package com.cafe.crm.service_abstract.user_service;

import com.cafe.crm.models.User;
import java.util.List;


public interface UserService {

    User getUserById(long id);

    User getUserByLogin(String name);

    List<User> findAll();

    User getUserByNameForShift(String name);

    void save(User user);

}

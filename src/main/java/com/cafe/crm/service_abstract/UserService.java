package com.cafe.crm.service_abstract;

import com.cafe.crm.models.User;
import java.util.List;

/**
 * Created by Sasha ins on 19.04.2017.
 */
public interface UserService {


    User getUserById(long id);

    User getUserByName(String name);

    List<User> findAll();

    User getUserByNameForShift(String name);

}

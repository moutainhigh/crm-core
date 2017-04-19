package com.cafe.crm.service_impl;

import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.models.User;
import com.cafe.crm.service_abstract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 19.04.2017.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;


    @Override
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }
}

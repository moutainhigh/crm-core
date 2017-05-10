package com.cafe.crm.service_impl;

import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by User on 19.04.2017.
 */
@Service
public class UserServiceImpl implements com.cafe.crm.service_abstract.UserService {


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

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByNameForShift(String name) {
        return userRepository.getUserByNameForShift(name);
    }


}

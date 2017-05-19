package com.cafe.crm.service_impl.userServiceImpl;

import com.cafe.crm.dao.UserRepository;
import com.cafe.crm.models.User;
import com.cafe.crm.service_abstract.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByLogin(String name) {
        return userRepository.getUserByLogin(name);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByNameForShift(String name) {
        return userRepository.getUserByNameForShift(name);
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }


}

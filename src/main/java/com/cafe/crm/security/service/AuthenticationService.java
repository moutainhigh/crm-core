package com.cafe.crm.security.service;

import com.cafe.crm.models.User;
import com.cafe.crm.service_impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Sasha ins on 17.04.2017.
 */
public class AuthenticationService implements UserDetailsService {


    @Autowired
    UserService userService;



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(login);

        if (user == null) {
            throw new UsernameNotFoundException("Username " + login + " not found");
        }

        return user;
    }
}

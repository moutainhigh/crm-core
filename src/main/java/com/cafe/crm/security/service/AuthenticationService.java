package com.cafe.crm.security.service;



import com.cafe.crm.models.User;
import com.cafe.crm.service_abstract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{
    @Autowired
    UserService userService;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByName(login);

        if (user == null) {
            throw new UsernameNotFoundException("Username " + login + " not found");
        }

        return user;
    }
}

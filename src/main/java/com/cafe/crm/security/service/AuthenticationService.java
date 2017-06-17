package com.cafe.crm.security.service;


import com.cafe.crm.dao.boss.BossRepository;
import com.cafe.crm.dao.manager.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationService implements UserDetailsService {

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

    @Autowired
    private BossRepository bossRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username = username.trim();
        UserDetails user = isEmail(username) ? loadByEmail(username) : loadByPhone(parsePhoneNumber(username));
        if (user == null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return user;
    }

    private UserDetails loadByEmail(String username) {
        UserDetails user = bossRepository.findByEmail(username);
        return (user != null) ? user : managerRepository.findByEmail(username);
    }

    private UserDetails loadByPhone(Long phone) {
        UserDetails user = bossRepository.findByPhone(phone);
        return (user != null) ? user : managerRepository.findByPhone(phone);
    }

    private boolean isEmail(String username) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
        return matcher.find();
    }

    private Long parsePhoneNumber(String phoneNumber) {
        String result = phoneNumber.replaceAll("[^0-9]+","");
        int startIndex = result.length() > 10 ? result.length() - 10 : 0;
        int endIndex = result.length();
        return Long.valueOf(result.substring(startIndex, endIndex));
    }
}

package com.cafe.crm.security.service;


import com.cafe.crm.models.user.Role;
import com.cafe.crm.models.user.User;
import com.cafe.crm.services.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = username.trim();
		User user = isEmail(username) ? loadByEmail(username) : loadByPhone(parsePhoneNumber(username));
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, grantedAuthorities);
	}

	private User loadByEmail(String email) {
		return userService.findByEmail(email);
	}

	private User loadByPhone(String phone) {
		return userService.findByPhone(phone);
	}

	private boolean isEmail(String username) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);
		return matcher.find();
	}

	private String parsePhoneNumber(String phoneNumber) {
		String result = phoneNumber.replaceAll("[^0-9]+", "");
		int startIndex = result.length() > 10 ? result.length() - 10 : 0;
		int endIndex = result.length();
		return result.substring(startIndex, endIndex);
	}
}

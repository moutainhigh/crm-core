package com.cafe.crm.utils.converters.user;


import com.cafe.crm.dto.UserDTO;
import com.cafe.crm.models.user.User;
import com.cafe.crm.utils.converters.position.PositionConverter;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

	public static UserDTO convertUserToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();

		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhone(user.getPhone());
		userDTO.setPassword(user.getPassword());
		userDTO.setPositions(PositionConverter.convertToListPositionDTO(user.getPositions()));
		userDTO.setShiftSalary(user.getShiftSalary());
		userDTO.setSalary(user.getSalary());
		userDTO.setBonus(user.getBonus());
		userDTO.setActivated(user.isActivated());
		userDTO.setEnabled(user.isEnabled());

		return userDTO;
	}

	public static List<UserDTO> convertListUsersToDTO(List<User> users) {
		List<UserDTO> result = new ArrayList<>();

		for (User user : users) {
			result.add(convertUserToUserDTO(user));
		}
		return result;
	}

}

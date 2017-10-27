package com.cafe.crm.services.impl.salary;


import com.cafe.crm.models.shift.UserSalaryDetail;
import com.cafe.crm.repositories.salary.UserSalaryDetailRepository;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserSalaryDetailServiceImpl {

	private UserSalaryDetailRepository userSalaryDetailRepository;
	private ShiftService shiftService;

	@Autowired
	public UserSalaryDetailServiceImpl(UserSalaryDetailRepository repository) {
		this.userSalaryDetailRepository = repository;
	}

	@Autowired
	public void setShiftService(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

	public void save(UserSalaryDetail userSalaryDetail) {
		userSalaryDetailRepository.save(userSalaryDetail);
	}

	public void save(List<UserSalaryDetail> userSalaryDetails) {
		userSalaryDetailRepository.save(userSalaryDetails);
	}

	public List<UserSalaryDetail> findByShiftId(Long shiftId) {
		return userSalaryDetailRepository.findByShiftId(shiftId);
	}

	public void deleteByUserIdAndShiftId(Long userId, Long shiftId) {
		userSalaryDetailRepository.deleteByUserIdAndShiftId(userId, shiftId);
	}
}

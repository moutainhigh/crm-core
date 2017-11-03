package com.cafe.crm.services.interfaces.salary;


import com.cafe.crm.models.shift.UserSalaryDetail;

import java.util.List;

public interface UserSalaryDetailService {
	void save(UserSalaryDetail userSalaryDetail);
	void save(List<UserSalaryDetail> userSalaryDetails);
	List<UserSalaryDetail> findByShiftId(Long shiftId);
	void deleteByUserIdAndShiftId(Long userId, Long shiftId);
}

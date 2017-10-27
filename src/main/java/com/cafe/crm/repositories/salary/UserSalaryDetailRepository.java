package com.cafe.crm.repositories.salary;


import com.cafe.crm.models.shift.UserSalaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSalaryDetailRepository extends JpaRepository<UserSalaryDetail, Long> {
	List<UserSalaryDetail> findByShiftId(Long shiftId);
	void deleteByUserIdAndShiftId (Long userId, Long shiftId);
}

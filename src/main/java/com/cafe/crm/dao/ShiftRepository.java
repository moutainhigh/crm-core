package com.cafe.crm.dao;

import com.cafe.crm.models.shift.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ShiftRepository extends JpaRepository<Shift, Long> {

	@Query("SELECT u FROM Shift u WHERE u.id =(select max(id) from Shift)") //последняя смена в базе
	Shift getLast();

}

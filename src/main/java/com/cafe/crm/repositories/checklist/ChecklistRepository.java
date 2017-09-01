package com.cafe.crm.repositories.checklist;


import com.cafe.crm.models.checklist.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

	@Query("SELECT c FROM Checklist c where c.onOpenShiftText is not null")
	List<Checklist> getAllForOpenShift();

	@Query("SELECT c FROM Checklist c where c.onCloseShiftText is not null")
	List<Checklist> getAllForCloseShift();
}

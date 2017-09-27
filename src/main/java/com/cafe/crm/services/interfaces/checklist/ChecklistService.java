package com.cafe.crm.services.interfaces.checklist;


import com.cafe.crm.models.checklist.Checklist;

import java.util.List;

public interface ChecklistService {

	Checklist find(Long id);

	void saveChecklistOnCloseShift(String text);

	void saveChecklistOnOpenShift(String text);

	void update(Checklist checklist);

	void delete(Long id);

	List<Checklist> getAll();

	List<Checklist> getAllForOpenShift();

	List<Checklist> getAllForCloseShift();
}

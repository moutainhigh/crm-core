package com.cafe.crm.services.impl.checklist;

import com.cafe.crm.exceptions.checklist.ChecklistDataException;
import com.cafe.crm.models.checklist.Checklist;
import com.cafe.crm.repositories.checklist.ChecklistRepository;
import com.cafe.crm.services.interfaces.checklist.ChecklistService;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistServiceIml implements ChecklistService{

	private final ChecklistRepository checklistRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public ChecklistServiceIml(ChecklistRepository checklistRepository, CompanyService companyService) {
		this.checklistRepository = checklistRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Checklist checklist) {
		checklist.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public Checklist find(Long id) {
		return checklistRepository.findOne(id);
	}

	@Override
	public void saveChecklistOnCloseShift(String text) {
		Checklist checklist = new Checklist();
		checklist.setOnCloseShiftText(text);
		setCompanyId(checklist);
		checklistRepository.save(checklist);
	}

	@Override
	public void saveChecklistOnOpenShift(String text) {
		Checklist checklist = new Checklist();
		checklist.setOnOpenShiftText(text);
		setCompanyId(checklist);
		checklistRepository.save(checklist);
	}

	@Override
	public void update(Checklist checklist) {
		Checklist checklistFromDB = checklistRepository.findOne(checklist.getId());
		if (checklistFromDB != null) {
			setCompanyId(checklist);
			checklistRepository.save(checklist);
		} else {
			throw new ChecklistDataException("Не найден указанный параметр");
		}
	}

	@Override
	public void delete(Long id) {
		checklistRepository.delete(id);
	}

	@Override
	public List<Checklist> getAll() {
		return checklistRepository.findByCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public List<Checklist> getAllForOpenShift() {
		return checklistRepository.getAllForOpenShiftAndCompanyId(companyIdCache.getCompanyId());
	}

	@Override
	public List<Checklist> getAllForCloseShift() {
		return checklistRepository.getAllForCloseShiftAndCompanyId(companyIdCache.getCompanyId());
	}


}

package com.cafe.crm.services.impl.note;

import com.cafe.crm.models.note.NoteRecord;
import com.cafe.crm.repositories.note.NoteRecordRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.note.NoteRecordService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteRecordServiceImpl implements NoteRecordService {

	private final NoteRecordRepository noteRecordRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public NoteRecordServiceImpl(NoteRecordRepository noteRecordRepository, CompanyService companyService) {
		this.noteRecordRepository = noteRecordRepository;
		this.companyService = companyService;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(NoteRecord noteRecord) {
		noteRecord.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public NoteRecord save(NoteRecord noteRecord) {
		setCompanyId(noteRecord);
		return noteRecordRepository.save(noteRecord);
	}
}

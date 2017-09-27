package com.cafe.crm.services.impl.note;

import com.cafe.crm.models.note.Note;
import com.cafe.crm.repositories.note.NoteRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.note.NoteService;
import com.cafe.crm.utils.CompanyIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;
	private final CompanyService companyService;
	private CompanyIdCache companyIdCache;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository, CompanyService companyService) {
		this.companyService = companyService;
		this.noteRepository = noteRepository;
	}

	@Autowired
	public void setCompanyIdCache(CompanyIdCache companyIdCache) {
		this.companyIdCache = companyIdCache;
	}

	private void setCompanyId(Note note) {
		note.setCompany(companyService.findOne(companyIdCache.getCompanyId()));
	}

	@Override
	public Note save(Note note) {
		setCompanyId(note);
		return noteRepository.save(note);
	}

	@Override
	public List<Note> findAllOrderingByEnableDescNameAsc() {
		return noteRepository.findByCompanyIdOrderByEnableDescNameAsc(companyIdCache.getCompanyId());
	}

	@Override
	public List<Note> findAllByEnableIsTrue() {
		return noteRepository.findByCompanyIdAndEnableIsTrue(companyIdCache.getCompanyId());
	}

	@Override
	public void delete(Long id) {
		noteRepository.delete(id);
	}

	@Override
	public void changeStatus(Long id, boolean enable) {
		Note note = noteRepository.findOne(id);
		if (note != null) {
			note.setEnable(enable);
			noteRepository.save(note);
		}
	}

}

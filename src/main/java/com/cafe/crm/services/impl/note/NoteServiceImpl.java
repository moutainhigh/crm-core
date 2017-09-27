package com.cafe.crm.services.impl.note;

import com.cafe.crm.models.note.Note;
import com.cafe.crm.repositories.note.NoteRepository;
import com.cafe.crm.services.interfaces.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@Override
	public Note save(Note note) {
		return noteRepository.save(note);
	}

	@Override
	public List<Note> findAllOrderingByEnableDescNameAsc() {
		return noteRepository.findAllByOrderByEnableDescNameAsc();
	}

	@Override
	public List<Note> findAllByEnableIsTrue() {
		return noteRepository.findAllByEnableIsTrue();
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

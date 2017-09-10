package com.cafe.crm.services.impl.note;

import com.cafe.crm.models.note.NoteRecord;
import com.cafe.crm.repositories.note.NoteRecordRepository;
import com.cafe.crm.services.interfaces.note.NoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteRecordServiceImpl implements NoteRecordService {

	private final NoteRecordRepository noteRecordRepository;

	@Autowired
	public NoteRecordServiceImpl(NoteRecordRepository noteRecordRepository) {
		this.noteRecordRepository = noteRecordRepository;
	}

	@Override
	public NoteRecord save(NoteRecord noteRecord) {
		return noteRecordRepository.save(noteRecord);
	}
}

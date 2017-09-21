package com.cafe.crm.configs.init;

import com.cafe.crm.models.note.Note;
import com.cafe.crm.services.interfaces.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitNotes {

	private final NoteService noteService;

	@Autowired
	public InitNotes(NoteService noteService) {
		this.noteService = noteService;
	}

	//@PostConstruct
	private void init() {
		Note note1 = new Note();
		note1.setName("Заметка 1");
		note1.setEnable(true);
		Note note2 = new Note();
		note2.setName("Заметка 2");
		note2.setEnable(false);

		noteService.save(note1);
		noteService.save(note2);
	}
}

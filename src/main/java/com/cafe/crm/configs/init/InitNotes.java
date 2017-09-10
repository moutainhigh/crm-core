package com.cafe.crm.configs.init;

import com.cafe.crm.models.checklist.Checklist;
import com.cafe.crm.models.note.Note;
import com.cafe.crm.services.interfaces.checklist.ChecklistService;
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

	@PostConstruct
	private void init() {
		Note note4 = new Note();
		note4.setName("Заметка 4");
		note4.setEnable(true);
		Note note1 = new Note();
		note1.setName("Заметка 1");
		note1.setEnable(true);
		Note note2 = new Note();
		note2.setName("Заметка 2");
		note2.setEnable(false);
		Note note3 = new Note();
		note3.setName("Заметка 3");
		note3.setEnable(true);


		noteService.save(note1);
		noteService.save(note2);
		noteService.save(note3);
		noteService.save(note4);
	}
}

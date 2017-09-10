package com.cafe.crm.repositories.note;

import com.cafe.crm.models.note.NoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRecordRepository extends JpaRepository<NoteRecord, Long> {
}

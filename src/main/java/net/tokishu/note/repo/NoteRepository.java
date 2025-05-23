package net.tokishu.note.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<net.tokishu.note.model.Note, UUID> {
}

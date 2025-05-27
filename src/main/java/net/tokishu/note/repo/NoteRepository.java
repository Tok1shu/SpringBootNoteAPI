package net.tokishu.note.repo;

import net.tokishu.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByAuthorUsername(String username);
    List<Note> findByAuthorUsernameOrderByCreatedAtDesc(String username);
}

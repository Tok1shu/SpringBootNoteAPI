package net.tokishu.note.repo;

import net.tokishu.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByAuthorUsername(String username);
    List<Note> findByAuthorUsernameOrderByCreatedAtDesc(String username);
    Optional<Note> findByPublicLinkAndIsPublicTrue(String publicLink);
    boolean existsByPublicLink(String publicLink);

    @Modifying
    @Query("UPDATE Note n SET n.author = :newUsername WHERE n.author = :oldUsername")
    void updateOwnerUsername(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername);
}

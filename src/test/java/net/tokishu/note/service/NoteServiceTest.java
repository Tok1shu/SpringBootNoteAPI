package net.tokishu.note.service;

import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.model.Note;
import net.tokishu.note.repo.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Используем тестовый профиль
@Transactional // Откатывает транзакции после каждого теста
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        // Очищаем БД перед каждым тестом
        noteRepository.deleteAll();
    }

    @Test
    void addAndGetAllNotes() {
        // Given
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setName("Test Note");
        noteRequest.setText("Test content");

        // When
        Note savedNote = noteService.add(noteRequest);
        List<Note> notes = noteService.getAll();

        // Then
        assertNotNull(savedNote);
        assertNotNull(savedNote.getUuid());
        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).getName());
        assertEquals("Test content", notes.get(0).getText());
    }

    @Test
    void findExistingNote() {
        // Given
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setName("Find Me");
        noteRequest.setText("Find content");
        Note savedNote = noteService.add(noteRequest);

        // When
        Note foundNote = noteService.find(savedNote.getUuid());

        // Then
        assertNotNull(foundNote);
        assertEquals("Find Me", foundNote.getName());
        assertEquals("Find content", foundNote.getText());
        assertEquals(savedNote.getUuid(), foundNote.getUuid());
    }

    @Test
    void findNonExistingNote_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();

        // When & Then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.find(nonExistingId)
        );

        assertTrue(exception.getMessage().contains("Note not found"));
    }

    @Test
    void updateExistingNote() {
        // Given
        NoteRequest originalRequest = new NoteRequest();
        originalRequest.setName("Original Name");
        originalRequest.setText("Original content");
        Note savedNote = noteService.add(originalRequest);

        NoteRequest updateRequest = new NoteRequest();
        updateRequest.setName("Updated Name");
        updateRequest.setText("Updated content");

        // When
        Note updatedNote = noteService.update(savedNote.getUuid(), updateRequest);

        // Then
        assertNotNull(updatedNote);
        assertEquals(savedNote.getUuid(), updatedNote.getUuid());
        assertEquals("Updated Name", updatedNote.getName());
        assertEquals("Updated content", updatedNote.getText());

        // Проверяем, что изменения сохранились в БД
        Note foundNote = noteService.find(savedNote.getUuid());
        assertEquals("Updated Name", foundNote.getName());
        assertEquals("Updated content", foundNote.getText());
    }

    @Test
    void updateNonExistingNote_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();
        NoteRequest updateRequest = new NoteRequest();
        updateRequest.setName("Update Name");
        updateRequest.setText("Update content");

        // When & Then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.update(nonExistingId, updateRequest)
        );

        assertTrue(exception.getMessage().contains("Note not found"));
    }

    @Test
    void deleteExistingNote() {
        // Given
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setName("Delete Me");
        noteRequest.setText("Delete content");
        Note savedNote = noteService.add(noteRequest);

        // When
        assertDoesNotThrow(() -> noteService.delete(savedNote.getUuid()));

        // Then
        List<Note> notes = noteService.getAll();
        assertTrue(notes.isEmpty());

        // Проверяем, что заметка действительно удалена
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.find(savedNote.getUuid())
        );
        assertTrue(exception.getMessage().contains("Note not found"));
    }

    @Test
    void deleteNonExistingNote_ShouldThrowException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();

        // When & Then
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.delete(nonExistingId)
        );

        assertTrue(exception.getMessage().contains("Note not found"));
    }

    @Test
    void getAllNotes_WhenEmpty() {
        // When
        List<Note> notes = noteService.getAll();

        // Then
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void getAllNotes_WithMultipleNotes() {
        // Given
        for (int i = 1; i <= 5; i++) {
            NoteRequest noteRequest = new NoteRequest();
            noteRequest.setName("Note " + i);
            noteRequest.setText("Content for note " + i);
            noteService.add(noteRequest);
        }

        // When
        List<Note> notes = noteService.getAll();

        // Then
        assertNotNull(notes);
        assertEquals(5, notes.size());

        // Проверяем, что все заметки уникальны по ID
        long uniqueIds = notes.stream()
                .map(Note::getUuid)
                .distinct()
                .count();
        assertEquals(5, uniqueIds);
    }
}
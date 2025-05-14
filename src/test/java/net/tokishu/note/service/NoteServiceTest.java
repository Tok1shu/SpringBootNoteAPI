package net.tokishu.note.service;

import net.tokishu.note.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NoteServiceTest {

    private NoteService noteService;

    @BeforeEach
    void setUp(){
        noteService = new NoteService();
    }

    @Test
    void addAndGetAllNotes(){
        Note note = new Note(1L, "QQ", "Test content");
        noteService.add(note);

        List<Note> notes = noteService.getAll();
        assertEquals(1, notes.size());
        assertEquals("QQ", notes.get(0).getName());
    }

    @Test
    void updateNote(){
        Note note = new Note(1L, "Old QQ", "Old test content");
        noteService.add(note);

        Note updatedNote = new Note(1L, "New day, new qq :3", "New test content");
        noteService.update(1L, updatedNote);

        Note result = noteService.getAll().get(0);
        assertEquals("New day, new qq :3", result.getName());
    }

    @Test
    void deleteNote(){
        Note noteToDelete = new Note(1L, "Bye bye :(", "content");
        noteService.add(noteToDelete);
        noteService.delete(1L);

        assertTrue(noteService.getAll().isEmpty());
    }

    @Test
    void findNote(){

        for (long i = 1; i <= 10; i++) {
            Note note = new Note(i, "Note " + i, "Content for note №" + i);
            noteService.add(note);
        }

        Note result = noteService.find(5L);

        assertNotNull(result, "Note should be found");
        assertEquals("Note 5", result.getName());
    }
}

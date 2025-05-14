package net.tokishu.note.service;

import net.tokishu.note.model.Note;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {
    private List<Note> notes = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<Note> getAll(){
        return notes;
    }

    public Note find(Long id) {
        for (Note note : notes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
    }


    public Note add(Note note){
        note.setId(idGenerator.getAndIncrement());
        notes.add(note);
        return note;
    }

    public Note update(Long id, Note updatedNote){
        for (Note note : notes){
            if (note.getId().equals(id)) {
                note.setName(updatedNote.getName());
                note.setText(updatedNote.getText());
                return note;
            }
        }
        throw new NoSuchElementException("Note not found");
    }

    public void delete(Long id){
        notes.removeIf(note -> note.getId().equals(id));
    }
}

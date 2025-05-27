package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.model.Note;
import net.tokishu.note.repo.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NoteService {

    public final NoteRepository noteRepository;

    public List<Note> getAll(){
        return noteRepository.findAll();
    }

    public Note find(UUID uuid) {
        return noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }


    public Note add(NoteRequest data){
        Note note = new Note();
            note.setName(data.getName());
            note.setText(data.getText());

            return noteRepository.save(note);
    }

    public Note update(UUID uuid, NoteRequest data){
        Note existing = noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
            existing.setName(data.getName());
            existing.setText(data.getText());

        return noteRepository.save(existing);
    }

    public void delete(UUID uuid){
        Note existing = noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        noteRepository.deleteById(uuid);
    }
}

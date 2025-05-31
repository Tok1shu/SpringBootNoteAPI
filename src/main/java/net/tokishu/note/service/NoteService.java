package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.dto.response.NoteResponse;
import net.tokishu.note.model.Note;
import net.tokishu.note.model.User;
import net.tokishu.note.repo.NoteRepository;
import net.tokishu.note.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    public final NoteRepository noteRepository;
    public final UserService userService;
    public final UserRepository userRepository;

    public List<NoteResponse> getAll() {
        User currentUser = userService.getCurrentUser();
        List<Note> notes;

        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            notes = noteRepository.findAll();
        } else {
            notes = noteRepository.findByAuthorUsername(currentUser.getUsername());
        }

        return notes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public NoteResponse find(UUID uuid) {
        Note note = noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        User currentUser = userService.getCurrentUser();
        checkOwnership(note, currentUser);
        return toResponse(note);
    }

    public NoteResponse add(NoteRequest data){
        User author = userService.getCurrentUser();

        Note note = new Note();
        note.setName(data.getName());
        note.setText(data.getText());
        note.setAuthor(author);

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    public NoteResponse update(UUID uuid, NoteRequest data) {
        Note existing = noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        User currentUser = userService.getCurrentUser();
        checkOwnership(existing, currentUser);

        existing.setName(data.getName());
        existing.setText(data.getText());

        return toResponse(noteRepository.save(existing));
    }

    public void delete(UUID uuid) {
        Note existing = noteRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        User currentUser = userService.getCurrentUser();
        checkOwnership(existing, currentUser);
        noteRepository.delete(existing);
    }

    private void checkOwnership(Note note, User user) {
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return;
        }

        if (!note.getAuthor().getUsername().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this note");
        }
    }

    private NoteResponse toResponse(Note note) {
        return NoteResponse.builder()
                .uuid(note.getUuid())
                .name(note.getName())
                .text(note.getText())
                .author(note.getAuthor().getUsername())
                .createdAt(note.getCreatedAt())
                .build();
    }
}

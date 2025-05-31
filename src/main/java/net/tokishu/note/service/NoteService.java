package net.tokishu.note.service;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.dto.response.NoteResponse;
import net.tokishu.note.model.Note;
import net.tokishu.note.model.User;
import net.tokishu.note.repo.NoteRepository;
import net.tokishu.note.util.CodeGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    public final NoteRepository noteRepository;
    public final UserService userService;

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

    public NoteResponse findByIdOrPublicLink(String idOrCode) {
        if (!StringUtils.hasText(idOrCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid identifier or public link");
        }

        Note note;
        if (isUuid(idOrCode)) {
            UUID id = UUID.fromString(idOrCode);
            note = noteRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

            User currentUser = userService.getCurrentUser();
            checkOwnership(note, currentUser);
        } else {
            note = noteRepository.findByPublicLinkAndIsPublicTrue(idOrCode)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        }

        return toResponse(note);
    }


    public NoteResponse add(NoteRequest data) {
        User author = userService.getCurrentUser();

        Note note = new Note();
        note.setName(data.getName());
        note.setText(data.getText());
        note.setAuthor(author);

        String code;

        int attempts = 0;
        do {
            code = CodeGenerator.generateCode();
            attempts++;
            if (attempts > 10) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate unique public link");
            }
        } while (noteRepository.existsByPublicLink(code));

        note.setPublicLink(code);

        note.setIsPublic(Boolean.TRUE.equals(data.getIsPublic()));

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
        existing.setIsPublic(Boolean.TRUE.equals(data.getIsPublic()));

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
        if ("ADMIN".equalsIgnoreCase(user.getRole())) { // TODO: use enum
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
                .isPublic(note.getIsPublic())
                .publicLink(note.getPublicLink())
                .author(note.getAuthor().getUsername())
                .createdAt(note.getCreatedAt())
                .build();
    }

    private boolean isUuid(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

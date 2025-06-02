package net.tokishu.note.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.tokishu.note.annotation.CurrentUser;
import net.tokishu.note.annotation.NullableCurrentUser;
import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.dto.response.NoteResponse;
import net.tokishu.note.model.User;
import net.tokishu.note.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/notes")
public class NotesController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAll(@CurrentUser User sender){
        return ResponseEntity.ok(noteService.getAll(sender));
    }

    @GetMapping("/{idOrCode}")
    public NoteResponse find(@PathVariable String idOrCode, @NullableCurrentUser User sender) {
        return noteService.findByIdOrPublicLink(idOrCode, sender);
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> add(@RequestBody @Valid NoteRequest note, @CurrentUser User sender){
        noteService.add(note, sender);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Note added"));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid, @RequestBody @Valid NoteRequest note, @CurrentUser User sender){
        noteService.update(uuid, note, sender);
        return ResponseEntity.ok(Map.of("message", "Note updated"));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid, @CurrentUser User sender){
        noteService.delete(uuid, sender);
        return ResponseEntity.ok(Map.of("message", "Note deleted"));
    }
}

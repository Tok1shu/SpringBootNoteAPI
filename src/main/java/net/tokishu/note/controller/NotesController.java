package net.tokishu.note.controller;

import lombok.RequiredArgsConstructor;
import net.tokishu.note.dto.request.NoteRequest;
import net.tokishu.note.model.Note;
import net.tokishu.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notes")
public class NotesController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(noteService.getAll());
    }

    @GetMapping("/{uuid}")
    public Note find(@PathVariable UUID uuid){
        return noteService.find(uuid);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody NoteRequest note){
        noteService.add(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Note added"));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid, @RequestBody NoteRequest note){
        noteService.update(uuid, note);
        return ResponseEntity.ok(Map.of("message", "Note updated"));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid){
        noteService.delete(uuid);
        return ResponseEntity.ok(Map.of(" message", "Note deleted"));
    }
}

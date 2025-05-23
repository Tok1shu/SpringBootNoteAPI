package net.tokishu.note.controller;

import net.tokishu.note.dto.NoteRequest;
import net.tokishu.note.model.Note;
import net.tokishu.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class NotesController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(noteService.getAll());
    }

    @GetMapping("/note/{uuid}")
    public Note find(@PathVariable UUID uuid){
        return noteService.find(uuid);
    }

    @PostMapping("/note")
    public ResponseEntity<?> add(@RequestBody NoteRequest note){
        noteService.add(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Note added"));
    }

    @PutMapping("/note/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid, @RequestBody NoteRequest note){
        noteService.update(uuid, note);
        return ResponseEntity.ok(Map.of("message", "Note updated"));
    }

    @DeleteMapping("/note/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid){
        noteService.delete(uuid);
        return ResponseEntity.ok(Map.of(" message", "Note deleted"));
    }
}

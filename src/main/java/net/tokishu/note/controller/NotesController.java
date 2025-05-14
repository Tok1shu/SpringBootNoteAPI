package net.tokishu.note.controller;

import net.tokishu.note.model.Note;
import net.tokishu.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotesController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public List<Note> getAll(){
        return noteService.getAll();
    }

    @PostMapping("/note")
    public Note add(@RequestBody Note note){
        return noteService.add(note);
    }

    @PutMapping("/note/{id}")
    public Note update(@PathVariable Long id, @RequestBody Note note){
        return noteService.update(id, note);
    }

    @DeleteMapping("/note/{id}")
    public void delete(@PathVariable Long id){
        noteService.delete(id);
    }
}

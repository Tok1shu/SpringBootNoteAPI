package net.tokishu.note.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String name;
    private String text;
}

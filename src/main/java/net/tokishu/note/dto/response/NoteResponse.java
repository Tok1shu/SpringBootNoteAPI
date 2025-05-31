package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NoteResponse {
    private UUID uuid;
    private String name;
    private String text;
    private Boolean isPublic;
    private String publicLink;
    private String author;
    private LocalDateTime createdAt;
}

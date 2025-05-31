package net.tokishu.note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequest {
    @NotBlank
    @Size(min=1, max=128)
    private String name;

    @NotBlank
    @Size(min=1, max=8192)
    private String text;

    @NotNull
    private Boolean isPublic;
}

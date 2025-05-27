package net.tokishu.note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min=1, max=24)
    private String username;

    @NotBlank
    @Size(min=6, max=42)
    private String password;
}

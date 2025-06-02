package net.tokishu.note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.tokishu.note.model.UserRole;

@Data
public class UserByAdminRequest {
    @NotBlank()
    @Size(min = 3, max = 24)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "The username can only contain letters, numbers, symbols and hyphens.")
    private String username;

    @NotBlank()
    @Size(min = 6, max = 42)
    private String password;

    @NotBlank
    private UserRole role;
}
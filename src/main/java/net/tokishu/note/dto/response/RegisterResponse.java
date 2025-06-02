package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Data;
import net.tokishu.note.model.UserRole;


@Data
@Builder
public class RegisterResponse {
    private String username;
    private UserRole role;
    private String message;
}

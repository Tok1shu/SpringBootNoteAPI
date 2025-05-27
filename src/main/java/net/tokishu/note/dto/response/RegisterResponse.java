package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private String role;
    private String message;
}

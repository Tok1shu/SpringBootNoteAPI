package net.tokishu.note.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UserResponse {
    private String username;
    private String role;
}
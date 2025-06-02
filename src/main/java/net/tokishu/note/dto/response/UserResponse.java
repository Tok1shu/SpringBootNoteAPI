package net.tokishu.note.dto.response;

import lombok.Data;
import lombok.Builder;
import net.tokishu.note.model.UserRole;

@Data
@Builder
public class UserResponse {
    private String username;
    private String gravatarUrl;
    private UserRole role;
}
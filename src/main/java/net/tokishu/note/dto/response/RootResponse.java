package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Data;
import net.tokishu.note.model.UserRole;

@Data
@Builder
public class RootResponse {
    private String username;
    private String password;
    private UserRole role;
}

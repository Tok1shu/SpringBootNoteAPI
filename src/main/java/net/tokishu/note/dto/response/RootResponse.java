package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RootResponse {
    private String username;
    private String password;
    private String role;
}

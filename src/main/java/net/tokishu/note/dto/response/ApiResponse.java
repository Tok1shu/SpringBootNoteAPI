package net.tokishu.note.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {
    private final int status;
    private final String message;
}
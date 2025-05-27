package net.tokishu.note.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.tokishu.note.dto.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ApiErrorResponse.builder()
                        .status(ex.getStatusCode().value())
                        .message(ex.getReason() != null ? ex.getReason() : "Unexpected error")
                        .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.builder()
                        .status(400)
                        .message("Invalid request body")
                        .build()
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ApiErrorResponse.builder()
                        .status(404)
                        .message("Endpoint not found")
                        .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ApiErrorResponse.builder()
                        .status(500)
                        .message("Internal server error")
                        .build()
                );
    }
}

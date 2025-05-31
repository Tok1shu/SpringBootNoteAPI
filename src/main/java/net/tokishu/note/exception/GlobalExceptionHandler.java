package net.tokishu.note.exception;

import jakarta.servlet.http.HttpServletRequest;
import net.tokishu.note.dto.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException validationEx) {
            return handleValidation(validationEx);
        }

        switch (ex.getClass().getSimpleName()) {
            case "ResponseStatusException" -> {
                ResponseStatusException rse = (ResponseStatusException) ex;
                return buildErrorResponse(rse.getStatusCode().value(), rse.getReason());
            }
            case "NoHandlerFoundException" -> {
                return buildErrorResponse(404, "Endpoint not found");
            }
            case "HttpMessageNotReadableException" -> {
                return buildErrorResponse(400, "Invalid request body");
            }
            case "HttpRequestMethodNotSupportedException" -> {
                return buildErrorResponse(405, "Method not allowed");
            }
            case "HttpMediaTypeNotSupportedException" -> {
                return buildErrorResponse(415, "Unsupported media type");
            }
            case "HttpMediaTypeNotAcceptableException" -> {
                return buildErrorResponse(406, "Not acceptable");
            }
            case "HttpRequestTimeoutException" -> {
                return buildErrorResponse(408, "Request timeout");
            }
            default -> {
                return buildErrorResponse(500, "Internal server error");
            }
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        return buildErrorResponse(400, message);
    }


    private ResponseEntity<ApiErrorResponse> buildErrorResponse(int status, String message) {
        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.builder()
                        .status(status)
                        .message(message != null ? message : "Unexpected error")
                        .build());
    }
}

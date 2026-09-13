package net.tokishu.note.shared.exception.model;

import net.tokishu.note.shared.exception.model.vo.Error;

import java.time.Instant;

public class ParentDomainException extends RuntimeException {
    private final Error error;
    private final Instant timestamp;

    public ParentDomainException(Error error, String message) {
        super(message);
        this.error = error;
        this.timestamp = Instant.now();
    }

    public String getError() {
        return error.value();
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

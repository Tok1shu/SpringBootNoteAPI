package net.tokishu.note.shared.exception.model.category;

import net.tokishu.note.shared.exception.model.ParentDomainException;
import net.tokishu.note.shared.exception.model.vo.Error;

public class InternalServerException extends ParentDomainException {
    private final String internalMessage;
    private final Throwable cause;

    public InternalServerException(String internalMessage) {
        this(internalMessage, null);
    }

    public InternalServerException(String internalMessage, Throwable cause) {
        super(new Error("INTERNAL_SERVER_ERROR"), "Internal Server Error", cause);
        this.internalMessage = internalMessage;
        this.cause = cause;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public Throwable getCause() {
        return cause;
    }
}

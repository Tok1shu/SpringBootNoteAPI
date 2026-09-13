package net.tokishu.note.shared.exception.infrastracture.inbound.rest;

import net.tokishu.note.shared.exception.model.ParentDomainException;
import net.tokishu.note.shared.exception.model.vo.Error;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpringExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ParentDomainException handleUnexpectedException(Exception ex) {
        return new ParentDomainException(new Error("unexpected_error"), ex.getMessage());
    }

}

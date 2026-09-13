package net.tokishu.note.shared.exception.model.category;

import net.tokishu.note.shared.exception.model.ParentDomainException;
import net.tokishu.note.shared.exception.model.vo.Error;

public class InternalServerException extends ParentDomainException {
    public InternalServerException(String internalMessage) {
        super(new Error("INTERNAL_SERVER_ERROR"), "Internal Server Error");
        System.out.println(internalMessage);
    }
}

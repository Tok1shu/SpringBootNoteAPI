package net.tokishu.note.shared.exception.model.vo;

import net.tokishu.note.shared.exception.model.category.InternalServerException;

import java.util.Objects;

public record Error(
        String value
) {
    public final static String ERROR_VALUE_FORMAT = "^[A-Z]+(_[A-Z]+)*$";

    public Error {
        Objects.requireNonNull(value, "value must not be null");
        validate(value);
    }

    private String validate(String value){
        if (!value.matches(ERROR_VALUE_FORMAT)) {
            throw new InternalServerException(
                    "Domain error value is not valid: \""
                            +value+ "\" it must contains regex: \"" + ERROR_VALUE_FORMAT + "\"");
        }
        return value;
    }
}

package net.tokishu.note.util;

import lombok.experimental.UtilityClass;
import net.tokishu.note.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class CheckAuthUtil {
    public void check(User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}

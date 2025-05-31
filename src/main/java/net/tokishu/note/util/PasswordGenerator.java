package net.tokishu.note.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789*!$%&@";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}

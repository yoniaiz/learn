package ws.utils;

import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }
}

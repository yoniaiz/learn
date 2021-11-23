package ws.exceptions;

public class AuthException extends RuntimeException {
    private static final long serialVersionUID = 799032048886915257L;

    public AuthException(String message) {
        super(message);
    }
}

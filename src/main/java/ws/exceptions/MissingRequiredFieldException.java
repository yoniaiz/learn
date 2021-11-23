package ws.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = -5049024846721628298L;

    public MissingRequiredFieldException(String message) {
        super(message);
    }
}

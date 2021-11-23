package ws.exceptions;

public class CouldNotCreateRecordException extends RuntimeException {
    private static final long serialVersionUID = -5049021236721628298L;

    public CouldNotCreateRecordException(String message) {
        super(message);
    }
}

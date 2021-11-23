package ws.exceptions;

public class NotFoundRecordException extends RuntimeException {
    private static final long serialVersionUID = 9087663296221108152L;

    public NotFoundRecordException(String message) {
        super(message);
    }
}
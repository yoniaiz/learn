package ws.exceptions;

public class CloudNotUpdateRecordException extends RuntimeException {
    private static final long serialVersionUID = 412321236721628298L;
    public CloudNotUpdateRecordException(String message) {
        super(message);
    }
}

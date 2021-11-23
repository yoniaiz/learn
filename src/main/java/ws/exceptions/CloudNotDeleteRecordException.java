package ws.exceptions;

public class CloudNotDeleteRecordException extends RuntimeException {
    private static final long serialVersionUID = 4439728168645453652L;

    public CloudNotDeleteRecordException(String message) {
        super(message);
    }
}
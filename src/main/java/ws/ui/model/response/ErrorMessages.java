package ws.ui.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field. please check fields"),
    RECORD_ALREADY_EXISTS("User already exists"),
    INTERNAL_SERVER_ERROR("Something went wrong"),
    RECORD_NOT_FOUND("Record not found"),
    AUTH_FAILED("Auth failed"),
    UPDATE_RECORD_FAIL("Cloud not update record"),
    DELETE_RECORD_FAIL("Cloud not delete record");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

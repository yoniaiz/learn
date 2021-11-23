package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CloudNotDeleteRecordExceptionMapper implements ExceptionMapper<CloudNotDeleteRecordException> {
    @Override
    public Response toResponse(CloudNotDeleteRecordException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(), ErrorMessages.DELETE_RECORD_FAIL.name(), "href");

        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}
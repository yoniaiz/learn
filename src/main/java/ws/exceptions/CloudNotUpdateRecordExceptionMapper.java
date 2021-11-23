package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CloudNotUpdateRecordExceptionMapper implements ExceptionMapper<CloudNotUpdateRecordException> {
    @Override
    public Response toResponse(CloudNotUpdateRecordException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(), ErrorMessages.UPDATE_RECORD_FAIL.name(), "href");

        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}

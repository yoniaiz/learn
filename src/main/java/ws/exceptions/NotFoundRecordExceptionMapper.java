package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundRecordExceptionMapper implements ExceptionMapper<NotFoundRecordException> {
    @Override
    public Response toResponse(NotFoundRecordException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(), ErrorMessages.RECORD_NOT_FOUND.name(), "href");

        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}

package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException> {
    @Override
    public Response toResponse(CouldNotCreateRecordException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(), ErrorMessages.RECORD_ALREADY_EXISTS.name(), "href");

        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
}

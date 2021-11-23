package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthExceptionMapper implements ExceptionMapper<AuthException> {
    @Override
    public Response toResponse(AuthException e) {
        ErrorMessage message = new ErrorMessage(e.getMessage(),
                ErrorMessages.AUTH_FAILED.name(),
                "href");

        return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
    }
}

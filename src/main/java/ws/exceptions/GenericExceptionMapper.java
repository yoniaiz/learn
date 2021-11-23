package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.INTERNAL_SERVER_ERROR.name(),
                "link");

        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}

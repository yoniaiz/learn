package ws.exceptions;

import ws.ui.model.response.ErrorMessage;
import ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    @Override
    public Response toResponse(MissingRequiredFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                                                     ErrorMessages.MISSING_REQUIRED_FIELD.name(),
                                                "link");

        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}

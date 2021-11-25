package ws.ui.entrypoints;

import ws.dto.UserDTO;
import ws.service.AuthService;
import ws.service.AuthServiceImpl;
import ws.ui.model.request.LoginCredentials;
import ws.ui.model.response.AuthDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(LoginCredentials credentials) {
        AuthDetails authDetails = new AuthDetails();

        AuthService authService = new AuthServiceImpl();
        UserDTO authenticateUser = authService.authenticate(credentials.getName(), credentials.getPassword());

        authService.resetSecurityCredentials(credentials.getPassword(), authenticateUser);
        String accessToken = authService.issueAccessToken(authenticateUser);

        authDetails.setId(authenticateUser.getUserId());
        authDetails.setToken(accessToken);

        Cookie cookie = new Cookie("userId", authDetails.getId());
        NewCookie newCookie = new NewCookie(cookie, "value", 60 * 60, false);
        return Response.status(Response.Status.OK).entity(authDetails).cookie(newCookie).build();
    }
}

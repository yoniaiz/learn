package ws.filters;

import ws.annitations.Secured;
import ws.dto.UserDTO;
import ws.exceptions.AuthException;
import ws.service.UserService;
import ws.service.UserServiceImpl;
import ws.utils.UserProfileUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader
                = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new AuthException("Auth header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        String userId = containerRequestContext
                        .getUriInfo()
                        .getPathParameters().getFirst("id");

        validateToken(token, userId);
    }

    private void validateToken(String token, String userId) {
        UserService usersService = new UserServiceImpl();
        UserDTO userProfile = usersService.getUser(userId);

        String completeToken = userProfile.getToken() + token;

        String securePassword = userProfile.getEncryptedPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;
        byte[] encryptedAccessToken = null;

        try {
            encryptedAccessToken = new UserProfileUtils()
                    .encrypt(securePassword,accessTokenMaterial);
        } catch (InvalidKeySpecException e) {
            Logger.getLogger(AuthFilter.class.getName()).log(Level.SEVERE, null, e);
            throw new AuthException(" filed to issue secure access token");
        }

        String encryptedAccessTokenBase64 = Base64.getEncoder().encodeToString(encryptedAccessToken);

        if(!encryptedAccessTokenBase64.equals(completeToken)){
            throw new AuthException("Auth token did not match");
        }
    }
}

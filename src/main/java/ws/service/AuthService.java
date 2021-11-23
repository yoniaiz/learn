package ws.service;

import ws.dto.UserDTO;
import ws.exceptions.AuthException;

public interface AuthService {
    UserDTO authenticate(String userName, String password) throws AuthException;
    String issueAccessToken(UserDTO userProfile) throws AuthException;

    void resetSecurityCredentials(String password, UserDTO authenticateUser);
}

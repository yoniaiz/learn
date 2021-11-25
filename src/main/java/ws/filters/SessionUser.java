package ws.filters;

import ws.dto.UserDTO;

import javax.security.auth.Subject;
import java.security.Principal;

public class SessionUser implements Principal {
    UserDTO sessionUser;

    public SessionUser(UserDTO userDTO) {
        super();
        this.sessionUser = userDTO;
    }

    public UserDTO getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(UserDTO sessionUser) {
        this.sessionUser = sessionUser;
    }

    @Override
    public String getName() {
        return sessionUser.getFirstname();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}

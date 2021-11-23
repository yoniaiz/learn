package ws.service;

import ws.dto.UserDTO;
import ws.exceptions.AuthException;
import ws.io.entity.dao.DAO;
import ws.io.entity.dao.MySQLDAO;
import ws.ui.model.response.ErrorMessages;
import ws.utils.UserProfileUtils;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthServiceImpl implements AuthService {
    private DAO database;

    @Override
    public UserDTO authenticate(String userName, String password) throws AuthException {
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUserByUserName(userName);

        if (storedUser == null) {
            throw new AuthException(ErrorMessages.AUTH_FAILED.getErrorMessage());
        }

        String encryptedPassword = new UserProfileUtils().
                generateSecurePassword(password, storedUser.getSalt());

        boolean authenticated = false;
        if (encryptedPassword != null &&
                encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())
        ) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthException(ErrorMessages.AUTH_FAILED.getErrorMessage());
        }

        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) throws AuthException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils()
                    .encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthServiceImpl.class.getName())
                    .log(Level.SEVERE, null, ex);

            throw new AuthException(ErrorMessages.AUTH_FAILED.getErrorMessage());
        }

        String encryptedAccessTokenBase64 = Base64.getEncoder().encodeToString(encryptedAccessToken);

        int tokenLength = encryptedAccessTokenBase64.length();

        String tokenToSaveInDb = encryptedAccessTokenBase64.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveInDb);

        updateUserProfile(userProfile);
        return returnValue;
    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAO();

        try {
            database.openConnection();
            database.getUsersDAO().updateUserProfile(userProfile);
        } finally {
            database.closeConnection();
        }
    }

    @Override
    public void resetSecurityCredentials(String password, UserDTO authenticateUser) {
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.generateSalt(30);

        String securePassword = userUtils.generateSecurePassword(password, salt);

        authenticateUser.setSalt(salt);
        authenticateUser.setEncryptedPassword(securePassword);

        updateUserProfile(authenticateUser);
    }


}

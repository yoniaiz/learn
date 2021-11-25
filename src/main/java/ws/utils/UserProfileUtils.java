package ws.utils;

import ws.dto.UserDTO;
import ws.exceptions.MissingRequiredFieldException;
import ws.ui.model.response.ErrorMessages;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class UserProfileUtils {
    private final int ITERATION = 10000;
    private final int KEY_LENGTH = 256;

    Utils utils;

    public UserProfileUtils() {
        this.utils = new Utils();
    }

    public String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public String generateUserId(int length) {
        return utils.generateRandomString(length);
    }

    public void validateRequiredFields(UserDTO userDTO) throws MissingRequiredFieldException {
        if (
                userDTO.getFirstname() == null ||
                        userDTO.getFirstname().isEmpty() ||
                        userDTO.getLastname() == null ||
                        userDTO.getLastname().isEmpty() ||
                        userDTO.getEmail() == null ||
                        userDTO.getEmail().isEmpty() ||
                        userDTO.getPassword() == null ||
                        userDTO.getPassword().isEmpty()
        ) {
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }

    public String generateSalt(int length) {
        return utils.generateRandomString(length);
    }

    public String generateSecurePassword(String password, String salt) {
        String returnValue;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    private byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATION, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("error hashing the password", e);
        } catch (InvalidKeySpecException e) {
            throw new AssertionError("error hashing the password", e);
        } finally {
            spec.clearPassword();
        }
    }

    public byte[] encrypt(String encryptedPassword, String accessTokenMaterial) throws InvalidKeySpecException {
        return hash(encryptedPassword.toCharArray(), accessTokenMaterial.getBytes());
    }

    public String getUserIdFromCookies(ContainerRequestContext containerRequestContext) {
        Cookie userIdCookie = containerRequestContext.getCookies().get("userId");
        return userIdCookie.getValue();
    }
}

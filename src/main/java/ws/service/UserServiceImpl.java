package ws.service;

import ws.dto.UserDTO;
import ws.exceptions.CloudNotDeleteRecordException;
import ws.exceptions.CloudNotUpdateRecordException;
import ws.exceptions.CouldNotCreateRecordException;
import ws.exceptions.NotFoundRecordException;
import ws.io.entity.dao.DAO;
import ws.io.entity.dao.MySQLDAO;
import ws.ui.model.response.ErrorMessages;
import ws.utils.UserProfileUtils;

import java.util.List;

public class UserServiceImpl implements UserService {
    DAO database;

    public UserServiceImpl() {
        this.database = new MySQLDAO();
    }

    UserProfileUtils userProfileUtils = new UserProfileUtils();

    @Override
    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;
        // validate the required fields
        userProfileUtils.validateRequiredFields(user);

        // check if user exits
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        String userID = userProfileUtils.generateUserId(30);
        user.setUserId(userID);

        String salt = userProfileUtils.generateSalt(30);
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);

        // record data to database
        returnValue = this.saveUser(user);
        return returnValue;
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO returnValue;

        try {
            this.database.openConnection();
            returnValue = this.database.getUsersDAO().getUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NotFoundRecordException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }

    private UserDTO saveUser(UserDTO user) {
        UserDTO userDTO;
        try {
            this.database.openConnection();
            userDTO = this.database.getUsersDAO().saveUser(user);
        } finally {
            this.database.closeConnection();
        }

        return userDTO;
    }

    @Override
    public UserDTO getUserByUserName(String email) {
        UserDTO userDTO = null;
        try {
            this.database.openConnection();
            userDTO = this.database.getUsersDAO().getUserByUserName(email);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e);
        } finally {
            this.database.closeConnection();
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> users = null;

        try {
            this.database.openConnection();
            users = this.database.getUsersDAO().getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return users;
    }

    @Override
    public void updateUserDetails(UserDTO storedUserDetails) {
        try {
            this.database.openConnection();
            this.database.getUsersDAO().updateUserProfile(storedUserDetails);
        } catch (Exception ex) {
            throw new CloudNotUpdateRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser(UserDTO storedUserDetails) {
        try {
            this.database.openConnection();
            this.database.getUsersDAO().deleteUserProfile(storedUserDetails);
        } catch (Exception ex) {
            throw new CloudNotDeleteRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }

        try {
            storedUserDetails = getUser(storedUserDetails.getUserId());
        } catch (NotFoundRecordException ex) {
            storedUserDetails = null;
        }

        if (storedUserDetails != null) {
            throw new CloudNotDeleteRecordException(ErrorMessages.DELETE_RECORD_FAIL.getErrorMessage());
        }
    }
}

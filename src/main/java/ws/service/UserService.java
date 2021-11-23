package ws.service;

import ws.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(String id);

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getUsers(int start, int limit);

    void updateUserDetails(UserDTO storedUserDetails);

    void deleteUser(UserDTO storedUserDetails);
}

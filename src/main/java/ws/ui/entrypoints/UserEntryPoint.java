package ws.ui.entrypoints;

import org.springframework.beans.BeanUtils;
import ws.annitations.Secured;
import ws.dto.ThemeDTO;
import ws.dto.UserDTO;
import ws.io.entity.ThemeEntity;
import ws.service.UserService;
import ws.service.UserServiceImpl;
import ws.ui.model.request.CreateUserRequestModel;
import ws.ui.model.request.UpdateUserRequestModel;
import ws.ui.model.response.CreateUserResponseModel;
import ws.ui.model.response.DeleteUserResponseModel;
import ws.ui.model.response.RequestOperation;
import ws.ui.model.response.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateUserResponseModel createUser(CreateUserRequestModel requestObject) {
        CreateUserResponseModel responseModel = new CreateUserResponseModel();

        // prepare user Data transfer object
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDTO);

        UserService usersService = new UserServiceImpl();
        UserDTO createdUserProfile = usersService.createUser(userDTO);

        // prepare response
        BeanUtils.copyProperties(createdUserProfile, responseModel);
        return responseModel;
    }

    @Secured
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CreateUserResponseModel getUserProfile(@PathParam("id") String id) {
        CreateUserResponseModel returnValue = new CreateUserResponseModel();

        UserService userService = new UserServiceImpl();
        UserDTO userProfile = userService.getUser(id);
        List<Long> likes = userProfile
                .getLikes()
                .stream()
                .map(ThemeEntity::getId)
                .collect(Collectors.toList());
        String[] ignoredProps = {"selectedTheme"};
        BeanUtils.copyProperties(userProfile, returnValue, ignoredProps);

        if (userProfile.getSelectedTheme() != null) {
            ThemeDTO theme = new ThemeDTO();
            BeanUtils.copyProperties(userProfile.getSelectedTheme(), theme);
            returnValue.setSelectedTheme(theme);
        }

        returnValue.setLikes(likes);
        return returnValue;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<CreateUserResponseModel> getUsers(
            @DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("20") @QueryParam("limit") int limit
    ) {
        List<CreateUserResponseModel> returnValue = new ArrayList<>();

        UserService userService = new UserServiceImpl();
        List<UserDTO> users = userService.getUsers(start, limit);

        for (UserDTO userDTO : users) {
            CreateUserResponseModel userModel = new CreateUserResponseModel();
            BeanUtils.copyProperties(userDTO, userModel);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateUserResponseModel updateUser(
            @PathParam("id") String id,
            UpdateUserRequestModel userDetails
    ) {
        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);

        if (userDetails.getFirstname() != null && !userDetails.getFirstname().isEmpty()) {
            storedUserDetails.setFirstname(userDetails.getFirstname());
        }
        if (userDetails.getLastname() != null && !userDetails.getLastname().isEmpty()) {
            storedUserDetails.setLastname(userDetails.getLastname());
        }

        userService.updateUserDetails(storedUserDetails);

        CreateUserResponseModel returnValue = new CreateUserResponseModel();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeleteUserResponseModel deleteUser(@PathParam("id") String id) {
        DeleteUserResponseModel returnValue = new DeleteUserResponseModel();
        returnValue.setId(id);
        returnValue.setRequestOperation(RequestOperation.DELETE);

        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);

        userService.deleteUser(storedUserDetails);

        returnValue.setResponseStatus(ResponseStatus.SUCCESS);

        return returnValue;
    }


}

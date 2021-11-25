package ws.ui.entrypoints;

import org.springframework.beans.BeanUtils;
import ws.annitations.Secured;
import ws.dto.ThemeDTO;
import ws.dto.ThemeTypeEnum;
import ws.dto.UserDTO;
import ws.filters.SessionUser;
import ws.io.entity.ThemeEntity;
import ws.service.ThemeService;
import ws.service.ThemeServiceImpl;
import ws.service.UserThemeService;
import ws.ui.model.request.ThemeRequestModel;
import ws.ui.model.response.LikeEum;
import ws.ui.model.response.LikeResponseModel;
import ws.ui.model.response.ThemeResponseModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("/themes")
public class ThemeEntryPoint {
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ThemeResponseModel createTheme(ThemeRequestModel themeToCreate) {
        ThemeResponseModel returnValue = new ThemeResponseModel();

        ThemeService themeService = new ThemeServiceImpl();
        ThemeDTO themeDTO = new ThemeDTO();
        String[] ignoredProps = {"type"};
        BeanUtils.copyProperties(themeToCreate, themeDTO, ignoredProps);

        if (themeToCreate.getType() != null) {
            themeDTO.setType(ThemeTypeEnum.typeByString(themeToCreate.getType()));
        }
        ThemeDTO savedTheme = themeService.createTheme(themeDTO);

        BeanUtils.copyProperties(savedTheme, returnValue);
        return returnValue;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ThemeResponseModel> getAllThemes(
            @DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("20") @QueryParam("limit") int limit
    ) {
        List<ThemeResponseModel> returnValue = new ArrayList<>();

        ThemeService themeService = new ThemeServiceImpl();
        List<ThemeDTO> themes = themeService.getAllThemes(start, limit);

        for (ThemeDTO theme : themes) {
            ThemeResponseModel newTheme = new ThemeResponseModel();
            BeanUtils.copyProperties(theme, newTheme);
            returnValue.add(newTheme);
        }

        return returnValue;
    }


    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{themeId}")
    public LikeResponseModel likeTheme(
            @Context SecurityContext securityContext,
            @PathParam("themeId") long themeId
    ) {
        SessionUser sessionUser = (SessionUser) securityContext.getUserPrincipal();
        UserDTO storedUserDetails = sessionUser.getSessionUser();
        UserThemeService userThemeService = new UserThemeService();
        LikeResponseModel likeResponseModel = new LikeResponseModel();

        likeResponseModel.setThemeId(themeId);
        if (userThemeService.likeTheme(storedUserDetails, themeId)) {
            likeResponseModel.setStatus(LikeEum.LIKED);
        } else {
            likeResponseModel.setStatus(LikeEum.UNLIKED);
        }

        return likeResponseModel;
    }

    @GET
    @Secured
    @Path("/defaultTheme/{themeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ThemeResponseModel setDefaultTheme(@Context SecurityContext securityContext, @PathParam("themeId") long themeId) {
        SessionUser sessionUser = (SessionUser) securityContext.getUserPrincipal();
        UserDTO storedUserDetails = sessionUser.getSessionUser();

        ThemeResponseModel returnValue = new ThemeResponseModel();
        UserThemeService userThemeService = new UserThemeService();
        ThemeEntity defaultTheme = userThemeService.setUserDefaultTheme(storedUserDetails, themeId);
        BeanUtils.copyProperties(defaultTheme, returnValue);
        return returnValue;
    }
}

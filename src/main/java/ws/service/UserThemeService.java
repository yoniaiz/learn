package ws.service;

import org.springframework.beans.BeanUtils;
import ws.dto.UserDTO;
import ws.exceptions.NotFoundRecordException;
import ws.io.entity.ThemeEntity;
import ws.io.entity.dao.DAO;
import ws.io.entity.dao.MySQLDAO;
import ws.ui.model.response.ErrorMessages;

import java.util.ArrayList;
import java.util.List;

public class UserThemeService {
    DAO database;

    public UserThemeService() {
        this.database = new MySQLDAO();
    }

    public boolean likeTheme(String userId, long themeId) {
        UserDTO updatedUser = new UserDTO();
        UserService userService = new UserServiceImpl();
        boolean returnValue = false;
        try {
            this.database.openConnection();
            UserDTO user = this.database.getUsersDAO().getUser(userId);
            BeanUtils.copyProperties(user, updatedUser);
            List<ThemeEntity> themes = updatedUser.getLikes();

            if (isThemeListContainerThemeId(themes, themeId)) {
                updatedUser.setLikes(ListWithoutLikedTheme(themes, themeId));
            } else {
                addLikeToList(themes, themeId);
                returnValue = true;
            }
        } finally {
            this.database.closeConnection();
        }

        userService.updateUserDetails(updatedUser);
        return returnValue;
    }

    private void addLikeToList(List<ThemeEntity> list, long id) {
        ThemeEntity theme = this.database.getThemesDAO().getTheme(id);
        if (theme == null) {
            throw new NotFoundRecordException(ErrorMessages.RECORD_NOT_FOUND.name());
        }

        list.add(theme);
    }

    private List<ThemeEntity> ListWithoutLikedTheme(List<ThemeEntity> list, long id) {
        List<ThemeEntity> filteredLikedThemes = new ArrayList<>();
        for (ThemeEntity themeDTO : list) {
            if (themeDTO.getId() != id) {
                filteredLikedThemes.add(themeDTO);
            }
        }

        return filteredLikedThemes;
    }

    private boolean isThemeListContainerThemeId(List<ThemeEntity> themes, long id) {
        return themes.stream().filter(t -> t.getId() == id).findFirst().orElse(null) != null;
    }

    public ThemeEntity setUserDefaultTheme(String userId, long themeId) {
        UserDTO user;
        ThemeEntity theme;

        try {
            this.database.openConnection();
            user = this.database.getUsersDAO().getUser(userId);
            theme = this.database.getThemesDAO().getTheme(themeId);
            user.setSelectedTheme(theme);
        } finally {
            this.database.closeConnection();
        }

        UserService userService = new UserServiceImpl();
        userService.updateUserDetails(user);

        return theme;
    }
}

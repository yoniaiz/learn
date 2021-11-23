package ws.service;

import ws.dto.ThemeDTO;
import ws.exceptions.CouldNotCreateRecordException;
import ws.io.entity.dao.DAO;
import ws.io.entity.dao.MySQLDAO;
import ws.ui.model.response.ErrorMessages;
import ws.utils.ThemeUtils;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {
    DAO database;

    public ThemeServiceImpl() {
        this.database = new MySQLDAO();
    }

    @Override
    public List<ThemeDTO> getAllThemes(int start, int limit) {
        List<ThemeDTO> themes = null;

        try {
            this.database.openConnection();
            themes = this.database.getThemesDAO().getThemes(start, limit);
        } catch (Exception ex) {
            throw ex;
        } finally {
            this.database.closeConnection();
        }

        return themes;
    }

    private ThemeDTO getThemeByColors(ThemeDTO themeRequest) {
        try {
            this.database.openConnection();
            return this.database.getThemesDAO().getThemeByColors(themeRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public ThemeDTO createTheme(ThemeDTO themeRequest) {
        ThemeUtils themeUtils = new ThemeUtils();
        themeUtils.validateTheme(themeRequest);

        ThemeDTO existingTheme = this.getThemeByColors(themeRequest);
        if (existingTheme != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        ThemeDTO themeDTO = this.saveTheme(themeRequest);
        return themeDTO;
    }

    private ThemeDTO saveTheme(ThemeDTO themeRequest) {
        ThemeDTO returnValue;
        try {
            this.database.openConnection();
            returnValue = this.database.getThemesDAO().saveTheme(themeRequest);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }
}

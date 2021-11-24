package ws.service;

import ws.dto.ThemeDTO;
import ws.exceptions.CouldNotCreateRecordException;
import ws.io.entity.dao.DAO;
import ws.io.entity.dao.MySQLDAO;
import ws.utils.ThemeUtils;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {
    DAO database;

    public ThemeServiceImpl() {
        this.database = new MySQLDAO();
    }

    @Override
    public List<ThemeDTO> getAllThemes(int start, int limit) {
        List<ThemeDTO> themes;

        try {
            this.database.openConnection();
            themes = this.database.getThemesDAO().getThemes(start, limit);
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

        ThemeDTO existingPalateTheme = this.getThemeByColors(themeRequest);
        ThemeDTO existingNameTheme = this.getThemeByName(themeRequest.getName());

        if (existingPalateTheme != null) {
            throw new CouldNotCreateRecordException("Theme with same color palate already exists - theme:" + existingPalateTheme.getName());
        }

        if (existingNameTheme != null) {
            throw new CouldNotCreateRecordException("Theme with same name already exists");
        }

        return this.saveTheme(themeRequest);
    }

    private ThemeDTO getThemeByName(String name) {
        ThemeDTO returnValue;
        try {
            this.database.openConnection();
            returnValue = this.database.getThemesDAO().getThemeByName(name);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
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

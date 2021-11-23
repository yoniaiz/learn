package ws.service;

import ws.dto.ThemeDTO;

import java.util.List;

public interface ThemeService {
    List<ThemeDTO> getAllThemes(int start, int limit);

    ThemeDTO createTheme(ThemeDTO ThemeRequest);
}

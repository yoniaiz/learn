package ws.utils;

import ws.dto.ThemeDTO;
import ws.exceptions.MissingRequiredFieldException;
import ws.ui.model.response.ErrorMessages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThemeUtils {
    Utils utils;

    public ThemeUtils() {
        this.utils = new Utils();
    }

    private boolean isValidHexColor(String color) {
        // Regex to check valid hexadecimal color code.
        String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        if (color == null) {
            return false;
        }

        Matcher m = p.matcher(color);

        return m.matches();
    }

    public void validateTheme(ThemeDTO theme) {
        if (
                theme.getName() == null ||
                        theme.getName().isEmpty() ||
                        !isValidHexColor(theme.getBackgroundColor()) ||
                        !isValidHexColor(theme.getFontColor()) ||
                        !isValidHexColor(theme.getPrimaryColor()) ||
                        !isValidHexColor(theme.getSecondaryColor())
        ) {
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

    }

}

package ws.dto;

public enum ThemeTypeEnum {
    DARK("Dark"), LIGHT("Light");

    private String type;

    ThemeTypeEnum(String type) {
        this.type = type;
    }

    public static ThemeTypeEnum typeByString(String palateType) {
        if (palateType.equalsIgnoreCase(DARK.type)) {
            return DARK;
        }

        return LIGHT;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

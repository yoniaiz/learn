package ws.ui.model.response;

import ws.dto.ThemeTypeEnum;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ThemeResponseModel {
    private long id;
    private String name;
    private String primaryColor;
    private String secondaryColor;
    private String fontColor;
    private ThemeTypeEnum type;
    private String backgroundColor;

    public ThemeTypeEnum getType() {
        return type;
    }

    public void setType(ThemeTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

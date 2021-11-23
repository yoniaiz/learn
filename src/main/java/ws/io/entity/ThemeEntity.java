package ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "themes")
public class ThemeEntity implements Serializable {
    private static final long serialVersionUID = 2850061470020395528L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String primaryColor;
    private String secondaryColor;
    private String fontColor;
    private String backgroundColor;

    @ManyToMany
    private List<UserEntity> userLikes;

    public List<UserEntity> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<UserEntity> userLikes) {
        this.userLikes = userLikes;
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

    public void setPrimaryColor(String primary) {
        this.primaryColor = primary;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondary) {
        this.secondaryColor = secondary;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
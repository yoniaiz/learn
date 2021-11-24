package ws.ui.model.response;

import ws.dto.ThemeDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CreateUserResponseModel {
    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private ThemeDTO selectedTheme;
    private List<Long> likes;

    public ThemeDTO getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(ThemeDTO selectedTheme) {
        this.selectedTheme = selectedTheme;
    }

    public List<Long> getLikes() {
        return likes;
    }

    public void setLikes(List<Long> likes) {
        this.likes = likes;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

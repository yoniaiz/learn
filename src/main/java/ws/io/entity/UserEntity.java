package ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -5125100339500119037L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String salt;
    private String encryptedPassword;
    private String token;
    @OneToOne
    private ThemeEntity selectedTheme;
    @ManyToMany
    private List<ThemeEntity> likes;

    public List<ThemeEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<ThemeEntity> likes) {
        this.likes = likes;
    }

    public ThemeEntity getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(ThemeEntity selectedTheme) {
        this.selectedTheme = selectedTheme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

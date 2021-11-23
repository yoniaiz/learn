package ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginCredentials {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

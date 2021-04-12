package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TempUser {
    private String login;
    private String oldPass;
    private String newPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

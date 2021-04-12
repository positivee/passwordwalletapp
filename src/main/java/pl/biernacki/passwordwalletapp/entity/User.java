package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "users" )
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, unique = true,length = 30 )
    private String login;
    @Column(nullable = false,length = 512)
    private String password;
    @Column(nullable = true, length = 20)
    private String salt;
    @Column(nullable = false, name="isPasswordKeptAsHash")
    public boolean isPasswordKeptAsHash;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY, mappedBy = "users")
    private List<Password> passwordList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_attempt_id", referencedColumnName = "id")
    private LoginAttempt loginAttempt;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean getIsPasswordKeptAsHash() {
        return isPasswordKeptAsHash;
    }



    public void setIsPasswordKeptAsHash(boolean passwordKeptAsHash) {
        isPasswordKeptAsHash = passwordKeptAsHash;
    }

   public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

/*    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/


    public void setPasswordKeptAsHash(boolean passwordKeptAsHash) {
        isPasswordKeptAsHash = passwordKeptAsHash;
    }

    public LoginAttempt getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(LoginAttempt loginAttempt) {
        this.loginAttempt = loginAttempt;
    }
}

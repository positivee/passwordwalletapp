package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Password")
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String passwordWeb;

    @Column
    private String webAddress;

    @Column
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User users;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY, mappedBy = "password")
    private List<SharePassword> sharePasswords;

    public List<SharePassword> getSharePasswords() {
        return sharePasswords;
    }

    public void setSharePasswords(List<SharePassword> sharePasswords) {
        this.sharePasswords = sharePasswords;
    }

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

    public String getPasswordWeb() {
        return passwordWeb;
    }

    public void setPasswordWeb(String passwordWeb) {
        this.passwordWeb = passwordWeb;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY, mappedBy = "password")
    private List<PasswordHistory> passwordHistoryList;

    public List<PasswordHistory> getPasswordHistoryList() {
        return passwordHistoryList;
    }

    public void setPasswordHistoryList(List<PasswordHistory> passwordHistoryList) {
        this.passwordHistoryList = passwordHistoryList;
    }
}

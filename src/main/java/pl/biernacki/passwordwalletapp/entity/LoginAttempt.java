package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loginAttempts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private boolean loginResult;
    private int attempts;
    private LocalDateTime successfulLoginTime;
    private LocalDateTime failedLoginTime;
    private LocalDateTime blockedUntil;

    @OneToOne(mappedBy = "loginAttempt")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLoginResult() {
        return loginResult;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public LocalDateTime getSuccessfulLoginTime() {
        return successfulLoginTime;
    }

    public void setSuccessfulLoginTime(LocalDateTime successfulLoginTime) {
        this.successfulLoginTime = successfulLoginTime;
    }

    public LocalDateTime getFailedLoginTime() {
        return failedLoginTime;
    }

    public void setFailedLoginTime(LocalDateTime failedLoginTime) {
        this.failedLoginTime = failedLoginTime;
    }

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(LocalDateTime blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

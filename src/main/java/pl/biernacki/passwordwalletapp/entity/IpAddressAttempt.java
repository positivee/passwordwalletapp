package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "ip_address_attempts")
@AllArgsConstructor
@NoArgsConstructor
public class IpAddressAttempt {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    private int attemptsCorrect;
    private int attemptsIncorrect;
    private LocalDateTime blockedUntil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getAttemptsCorrect() {
        return attemptsCorrect;
    }

    public void setAttemptsCorrect(int attemptsCorrect) {
        this.attemptsCorrect = attemptsCorrect;
    }

    public int getAttemptsIncorrect() {
        return attemptsIncorrect;
    }

    public void setAttemptsIncorrect(int attemptsIncorrect) {
        this.attemptsIncorrect = attemptsIncorrect;
    }

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(LocalDateTime blockedUntil) {
        this.blockedUntil = blockedUntil;
    }
}

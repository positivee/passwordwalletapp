package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "password_history")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String oldLogin;

    @Column(name = "password")
    private String oldPass;

    @Column(name = "web_address")
    private String oldWebAddress;

    @Column(name = "description")
    private String oldDescription;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Password password;
}

package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="share_password")
public class SharePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_login")
    private String ownerLogin;

    @Column(name = "recipient_login")
    private String borrowerLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    private Password password;


}

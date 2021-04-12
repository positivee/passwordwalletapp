package pl.biernacki.passwordwalletapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Actions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "function_name")
    private String functionName;

    @Column(name = "time")
    private LocalDateTime time;
}

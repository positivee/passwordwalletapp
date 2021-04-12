package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.LoginAttempt;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
        boolean existsByUserLogin(String login);
}
package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.Actions;
import pl.biernacki.passwordwalletapp.entity.PasswordHistory;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findAllByPasswordId(Long id);
}

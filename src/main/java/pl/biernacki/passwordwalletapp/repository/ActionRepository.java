package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.Actions;

import java.util.List;

public interface ActionRepository extends JpaRepository<Actions, Long> {
    List<Actions> findAllByUsername(String username);
}

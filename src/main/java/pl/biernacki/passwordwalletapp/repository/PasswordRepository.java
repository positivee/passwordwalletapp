package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.Actions;
import pl.biernacki.passwordwalletapp.entity.Password;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {


}


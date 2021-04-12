package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.SharePassword;

import java.util.List;

public interface SharePasswordRepository extends JpaRepository<SharePassword, Long> {
    List<SharePassword> findAllByBorrowerLogin(String login);
    boolean existsByBorrowerLoginAndOwnerLogin(String login, String loginOwner);
    boolean existsByPasswordId(Long id);

}

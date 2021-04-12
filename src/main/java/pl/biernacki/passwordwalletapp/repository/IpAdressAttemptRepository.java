package pl.biernacki.passwordwalletapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt;

public interface IpAdressAttemptRepository extends JpaRepository<IpAddressAttempt, Long> {
    boolean existsByIpAddress(String address);
    IpAddressAttempt findByIpAddress(String address);
    void deleteByIpAddress(String address);
}
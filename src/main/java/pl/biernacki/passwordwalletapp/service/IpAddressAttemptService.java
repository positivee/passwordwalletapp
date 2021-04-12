package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt;
import pl.biernacki.passwordwalletapp.repository.IpAdressAttemptRepository;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class IpAddressAttemptService {
    private final IpAdressAttemptRepository addressAttemptRepository;

    public IpAddressAttempt saveAddressAttempt(String address){
        IpAddressAttempt ipAddressAttempt = new IpAddressAttempt();
        ipAddressAttempt.setAttemptsCorrect(0);
        ipAddressAttempt.setAttemptsIncorrect(0);
        ipAddressAttempt.setBlockedUntil(LocalDateTime.now());
        ipAddressAttempt.setIpAddress(address);

        return addressAttemptRepository.save(ipAddressAttempt);
    }

    public boolean addressExists(String address){
        return addressAttemptRepository.existsByIpAddress(address);
    }

    @Transactional
    public IpAddressAttempt addressAddIncorrectAttempts(String address){
        IpAddressAttempt addressAttempt = addressAttemptRepository.findByIpAddress(address);
        addressAttempt.setAttemptsIncorrect(addressAttempt.getAttemptsIncorrect() + 1);

        switch (addressAttempt.getAttemptsIncorrect()){
            case 2:
                addressAttempt.setBlockedUntil(LocalDateTime.now().plusSeconds(5));
                break;
            case 3:
                addressAttempt.setBlockedUntil(LocalDateTime.now().plusSeconds(10));
                break;
            case 4:
                addressAttempt.setBlockedUntil(LocalDateTime.now().plusYears(100));
                break;
        }

        return addressAttempt;

    }

    @Transactional
    public IpAddressAttempt addressAddCorrectAttempts(String address){
        IpAddressAttempt addressAttempt = addressAttemptRepository.findByIpAddress(address);
        addressAttempt.setAttemptsCorrect(addressAttempt.getAttemptsCorrect() + 1);
        addressAttempt.setAttemptsIncorrect(0);
        return addressAttempt;
    }

    public boolean verifyAddressAttempts(String address){
        IpAddressAttempt addressAttempt = addressAttemptRepository.findByIpAddress(address);
        return LocalDateTime.now().isAfter(addressAttempt.getBlockedUntil());
    }

    public IpAddressAttempt getAddressAttempt(String address){
        return addressAttemptRepository.findByIpAddress(address);
    }

    @Transactional
    public void deleteByAddress(String address){
        addressAttemptRepository.deleteByIpAddress(address);
    }
}

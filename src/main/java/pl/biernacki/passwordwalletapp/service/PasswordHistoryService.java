package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.biernacki.passwordwalletapp.entity.PasswordHistory;
import pl.biernacki.passwordwalletapp.repository.PasswordHistoryRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PasswordHistoryService {
    private final PasswordHistoryRepository passwordHistoryRepository;

    public List<PasswordHistory> findAllByPasswordId(Long id){
        return passwordHistoryRepository.findAllByPasswordId(id);
    }

}

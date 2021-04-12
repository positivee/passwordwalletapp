package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.biernacki.passwordwalletapp.entity.Actions;
import pl.biernacki.passwordwalletapp.repository.ActionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionsRepository;

    public void saveAction(String functionName, String username){
        Actions action = new Actions();
        action.setFunctionName(functionName);
        action.setTime(LocalDateTime.now());
        action.setUsername(username);
        actionsRepository.save(action);
    }

    public List<Actions> getActionsForUser(String username){
        return actionsRepository.findAllByUsername(username);
    }
}
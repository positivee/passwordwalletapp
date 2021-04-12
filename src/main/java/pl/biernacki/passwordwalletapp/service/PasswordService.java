package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.biernacki.passwordwalletapp.Encryption.Aes;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.PasswordHistory;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.PasswordRepository;
import pl.biernacki.passwordwalletapp.repository.PasswordHistoryRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;

    public boolean existById(Long id){ return passwordRepository.existsById(id); }

    public Password getPassword(Long id){
        return passwordRepository.getOne(id);
    }
        /*saving to database password*/
    public Password savePassword(Password password, User user) throws Exception {
        password.setId(null);
        password.setPasswordWeb(Aes.encrypt(password.getPasswordWeb(), user.getPasswordHash()));
        password.setUsers(user);
        password = passwordRepository.save(password);
        user.getPasswordList().add(password);
        return password;
    }
    public void deletePasswordById(Long id){
        passwordRepository.deleteById(id);
    }

 /*   *//* edit password method*//*
    public Password editPasswordById(Password password, Password toSave, User user) throws Exception {
        password.setLogin(toSave.getLogin());
        password.setDescription(toSave.getDescription());
        if(!toSave.getPasswordWeb().isBlank())
            password.setPasswordWeb(Aes.encrypt(toSave.getPasswordWeb(), user.getPasswordHash()));
        password.setWebAddress(toSave.getWebAddress());
        passwordRepository.save(password);
        return password;
    }*/
 @Transactional
 public Password editPasswordById(Password password, Password toSave, User user) throws Exception {
     PasswordHistory passwordHistory = new PasswordHistory();
     passwordHistory.setOldLogin(password.getLogin());
     passwordHistory.setOldDescription(password.getDescription());
     passwordHistory.setOldPass(password.getPasswordWeb());
     passwordHistory.setOldWebAddress(password.getWebAddress());
     passwordHistory.setTime(LocalDateTime.now());
     passwordHistory.setPassword(password);
     passwordHistoryRepository.save(passwordHistory);

     password.setLogin(toSave.getLogin());
     password.setDescription(toSave.getDescription());
     if(!toSave.getPasswordWeb().isBlank())
         password.setPasswordWeb(Aes.encrypt(toSave.getPasswordWeb(), user.getPasswordHash()));
     password.setWebAddress(toSave.getWebAddress());

     return password;
 }
    @Transactional
    public Password restorePassword(Long passId, Long historyId){
        Password password = passwordRepository.getOne(passId);
        PasswordHistory passwordHistory = passwordHistoryRepository.findById(historyId).get();

        Password tempPassword = new Password();
        tempPassword.setLogin(password.getLogin());
        tempPassword.setDescription(password.getDescription());
        tempPassword.setPasswordWeb(password.getPasswordWeb());
        tempPassword.setWebAddress(password.getWebAddress());

        password.setPasswordWeb(passwordHistory.getOldPass());
        password.setWebAddress(passwordHistory.getOldWebAddress());
        password.setDescription(passwordHistory.getOldDescription());
        password.setLogin(passwordHistory.getOldLogin());

        passwordHistory.setOldPass(tempPassword.getPasswordWeb());
        passwordHistory.setOldLogin(tempPassword.getLogin());
        passwordHistory.setOldWebAddress(tempPassword.getWebAddress());
        passwordHistory.setOldDescription(tempPassword.getDescription());
        passwordHistory.setTime(LocalDateTime.now());
        passwordHistoryRepository.save(passwordHistory);

        return password;
    }
}
